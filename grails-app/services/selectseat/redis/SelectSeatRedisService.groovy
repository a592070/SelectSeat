package selectseat.redis


import grails.gorm.services.Service
import grails.plugins.redis.RedisService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.util.CollectionUtils
import redis.clients.jedis.Jedis
import redis.clients.jedis.Pipeline
import redis.clients.jedis.Transaction
import selectseat.Seat
import selectseat.User
import selectseat.Zone

import java.util.concurrent.CopyOnWriteArrayList

@Service
@Component
class SelectSeatRedisService {

//    static final String MUTEX_LOCK = "MUTEX_LOCK_"
//    static final String MUTEX_LOCK_ON = "1"
//    static final String MUTEX_LOCK_OFF = "0"

    // ZONE_SEAT_KEY + zoneId : {
    //      ZONE_SEAT_FIELD_ROW_INDEX + rowIndex + ZONE_SEAT_FIELD_INTERPOINT + ZONE_SEAT_FIELD_COLUMN_INDEX + colIndex : status
    //      rowIndex + ZONE_SEAT_FIELD_INTERPOINT + colIndex : status
    //      }
    // ZONE:01 = {
    //      ROW:01_COL:01 = 0
    //      01_01 = 0
    // }
    static final String ZONE_PREFIX = "ZONE:"
    static final String ROW_PREFIX =  "ROW:"
    static final String COLUMN_PREFIX = "COL:"
    static final String INTERPOINT = "_"
    static final String ZONE_SEAT_VALUE_DISABLED = Byte.MIN_VALUE
    static final String ZONE_SEAT_VALUE_EMPTY = 0
    static final String ZONE_SEAT_VALUE_RESERVED = 1
//    static final String ZONE_SEAT_VALUE_ = ""

    static final String INDEX_ZONE_KEY_PREFIX = "INDEX:ZONE:"

    static final String OCCUPY_KEY_USR_PREFIX = "OCCUPY:USR:"
    static final String OCCUPY_KEY_ZONE_PREFIX = ":ZONE:"
    static final String MUTEX_LOCK_ZONE_KEY_PREFIX = "MUTEX_LOCK_ZONE:"

    static final String INDEX_ZONE_TEMP_KEY = "TEMP:INDEX:ZONE:"
    static final String OCCUPY_KEY_PATTERN_ALL_USER_IN_ZONE = OCCUPY_KEY_USR_PREFIX + "*" + OCCUPY_KEY_ZONE_PREFIX

    static final int LOCK_EXPIRE_DEFAULT_TIMEOUT = 30*1000
    static final int LOCK_WAIT_DEFAULT_TIMEOUT = 10*1000


    @Autowired
    RedisService redisService

    def deleteKey(String key){
        redisService.withRedis { Jedis jedis ->
            jedis.del(key)
        }
    }

    /**
     * 設定鎖
     * @param lockKey
     * @param value
     * @param expireTime    過期時間10s
     * @return
     */
    def setMutexLock(String lockKey, String value, long expireTime=LOCK_EXPIRE_DEFAULT_TIMEOUT){
        redisService.withRedis { Jedis jedis ->
            return jedis.set(lockKey, value, "NX", "PX", expireTime)
        }
    }
    /**
     * 釋放前檢查value與上鎖時設定value相同，避免釋放錯誤
     * @param lockKey
     * @param value     上鎖時設定的value
     * @return
     */
    def releaseMutexLock(String lockKey, String value){
        redisService.withRedis {Jedis jedis ->
            if(jedis.get(lockKey) == value) deleteKey(lockKey)
        }
    }
    def releaseMultipleLock(Set<String> lockKey, String value){
        lockKey.each {
            releaseMutexLock(it, value)
        }
    }

    /**
     * 上鎖與釋放
     * @param lockKey   MUTEX_LOCK_ZONE_KEY_PREFIX + zone_id
     * @param closure   執行內容
     * @param maxWait   等待時間30s
     * @return
     */
    def withLock(String lockKey, Closure closure, int maxWait=LOCK_WAIT_DEFAULT_TIMEOUT){
        String value = UUID.randomUUID()
        long expireAt = System.currentTimeMillis()+maxWait
        try{
            while(setMutexLock(lockKey, value)){
                if(System.currentTimeMillis() > expireAt) return false

                Thread.sleep(Math.random()*50 as long)
            }
            closure()
        }catch(Exception e){
            e.printStackTrace()
        }finally{
            releaseMutexLock(lockKey, value)
        }
        return true
    }
    def withMultipleLock(Set<String> lockKeys, Closure closure, int maxWait=LOCK_WAIT_DEFAULT_TIMEOUT, int expireTime=LOCK_EXPIRE_DEFAULT_TIMEOUT){
        List<String> needLocking = [] as CopyOnWriteArrayList
        needLocking.addAll(lockKeys)

        long expireAt = System.currentTimeMillis()+maxWait
        String value = UUID.randomUUID()
        try{
            while(System.currentTimeMillis() < expireAt){

                List results = redisService.withPipeline ({ Pipeline redis ->
                    needLocking.each {
                        redis.set(it, value, "NX", "PX", expireTime)
                    }
                }, true)


                boolean isHoldAllLock = true
                // 已獲取的鎖用 locked 存起來，並從 needLocking 移除
                results.eachWithIndex { it, index ->
                    if(!it) isHoldAllLock = false
                }

                // 當一次獲得所有鎖，執行closure內容
                // 否則，刪除所有鎖，包含已獲取、沒獲取
                if(isHoldAllLock){
                    closure()
                    break
                }else {
                    releaseMultipleLock(lockKeys, value)
                }

                Thread.sleep(Math.random()*50 as long)
            }
        }catch(Exception e){
            e.printStackTrace()
        }finally{
            releaseMultipleLock(lockKeys, value)
        }
    }


    /**
     * 初始化ZONE:{zone_id} 與 INDEX:ZONE:{zone_id}
     * @param zone
     * @param disableSeat   禁用的位置
     * @return
     */
    Map<String, String> setZoneSeats(Zone zone, Set<List<Integer>> disableSeat) {
        String zoneKey = ZONE_PREFIX + zone.id
        String zoneIndexKey = INDEX_ZONE_KEY_PREFIX + zone.id

        Map map = [:]

        String lockKey = MUTEX_LOCK_ZONE_KEY_PREFIX + zone.id

        for (i in 0..<zone.rowCount) {
            for (j in 0..<zone.columnCount) {
                String fieldName = ROW_PREFIX + i + INTERPOINT + COLUMN_PREFIX + j
                String fieldValue = ZONE_SEAT_VALUE_EMPTY
                if ([i, j] in disableSeat) fieldValue = ZONE_SEAT_VALUE_DISABLED

                map.put(fieldName, fieldValue)
            }
        }

        withLock(lockKey, {
            redisService.withTransaction { Transaction transaction ->
                transaction.hmset(zoneKey, map)
                transaction.zadd(zoneIndexKey, map)
            }
        })
        return map
    }
    Map<String, String> getZoneSeats(Zone zone){
        String zoneKey = ZONE_PREFIX + zone.id
        Map<String, String> map = [:]
        redisService.withRedis { Jedis jedis ->
            map = jedis.hgetAll(zoneKey)
            jedis.hgetAll()
        }
        return map
    }

    Long countZoneEmptySeat(Long zoneId){
        Long count = 0

        String tmpIndexKey = INDEX_ZONE_TEMP_KEY + zoneId

        String lockKey = MUTEX_LOCK_ZONE_KEY_PREFIX + zoneId
        withLock(lockKey, {
            redisService.withRedis { Jedis redis ->
                if (!redis.exists(tmpIndexKey)) updateTempIndexZone(zoneId)
                count = redis.zcount(tmpIndexKey, ZONE_SEAT_VALUE_EMPTY, ZONE_SEAT_VALUE_EMPTY)
            }
        })
        return count
    }

    Set<String> getZoneEmptySeats(Long zoneId){
        def emptySeat = []

        String tmpIndexKey = INDEX_ZONE_TEMP_KEY + zoneId

        String lockKey = MUTEX_LOCK_ZONE_KEY_PREFIX + zoneId
        withLock(lockKey, {
            redisService.withRedis { Jedis redis ->
                if (!redis.exists(tmpIndexKey)) {
                    updateTempIndexZone(zoneId)
                }
                emptySeat = redis.zrangeByScore(tmpIndexKey, ZONE_SEAT_VALUE_EMPTY, ZONE_SEAT_VALUE_EMPTY)
            }
        })

        return emptySeat
    }


    Seat setUserOccupySeat(Seat oldSeat, int rowIndex, int columnIndex) {
        String occupyUserKey = OCCUPY_KEY_USR_PREFIX + oldSeat.userId + OCCUPY_KEY_ZONE_PREFIX + oldSeat.zoneId

        String occupyUserOldValue = ROW_PREFIX + oldSeat.rowIndex + INTERPOINT + COLUMN_PREFIX + oldSeat.columnIndex
        String occupyUserNewValue = ROW_PREFIX + rowIndex + INTERPOINT + COLUMN_PREFIX + columnIndex

        String seatMapKey = ZONE_PREFIX + oldSeat.zoneId
        String seatMapField = occupyUserNewValue

        String lockKey = MUTEX_LOCK_ZONE_KEY_PREFIX + oldSeat.zoneId
        withLock(lockKey, {
            redisService.withRedis { Jedis redis ->
                redis.zrem(occupyUserKey, occupyUserOldValue)
                redis.zadd(occupyUserKey, ZONE_SEAT_VALUE_RESERVED, occupyUserNewValue)
            }

            updateTempIndexZone(oldSeat.zoneId)
        })

        oldSeat.setRowIndex(rowIndex)
        oldSeat.setColumnIndex(columnIndex)
        return oldSeat
    }

    def setUserOrderSeat(User user, Set<Seat> seatList) {
//        Set<String> occupyUserKeys = []
//        Set<String> indexZoneList = []
        Set<String> zoneList = []
        Map<Long, Map<String, String>> seatMap = [:]
        seatList.each {
//            occupyUserKeys << OCCUPY_KEY_USR_PREFIX + user.id + OCCUPY_KEY_ZONE_PREFIX + it.zoneId
//            indexZoneList << INDEX_ZONE_KEY_PREFIX+ it.zoneId
            zoneList << (it.zoneId as String)
            seatMap.put(ROW_PREFIX + it.rowIndex + INTERPOINT + COLUMN_PREFIX + it.columnIndex, ZONE_SEAT_VALUE_RESERVED.toString())
        }


        redisService.withRedis {Jedis redis ->
            zoneList.each {
                String occupyUserKey = OCCUPY_KEY_USR_PREFIX + user.id + OCCUPY_KEY_ZONE_PREFIX + it
                String indexKey = INDEX_ZONE_KEY_PREFIX + it
                redis.zunionstore(indexKey, indexKey, occupyUserKey)
                //TODO
            }
        }

    }

    /**
     * 更新區域暫存座位表，由呼叫者上鎖
     * @param zoneId
     * @param timeout   10*60 s
     * @return
     */
    private def updateTempIndexZone(Long zoneId, int timeout=10*60){
        String lockKey = MUTEX_LOCK_ZONE_KEY_PREFIX + zoneId
        String pattern = OCCUPY_KEY_PATTERN_ALL_USER_IN_ZONE + zoneId
        String tmpIndexKey = INDEX_ZONE_TEMP_KEY + zoneId
        redisService.withRedis { Jedis redis ->
            def keys = redis.keys(pattern)
            // 加入原始zone index表，再進行聯集
            keys.add(INDEX_ZONE_KEY_PREFIX + zoneId)
            redis.zunionstore(tmpIndexKey, keys as String[])
            redis.expire(tmpIndexKey, timeout)
        }
    }





    def test(Long zoneId, int num){
        String zoneKey = ZONE_PREFIX + zoneId
        redisService.withRedis { Jedis jedis ->
        }

    }



}
