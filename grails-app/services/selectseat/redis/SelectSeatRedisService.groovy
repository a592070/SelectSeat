package selectseat.redis


import grails.gorm.services.Service
import grails.plugins.redis.RedisService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import redis.clients.jedis.Jedis
import redis.clients.jedis.JedisMonitor
import redis.clients.jedis.Transaction
import selectseat.Seat
import selectseat.Zone

@Service
@Component
class SelectSeatRedisService {

    // EMPTY_SEAT_KEY + eventId
//    static final String EMPTY_SEAT_KEY = "EMPTY_SEAT_KEY_"
    // ZONE_SEAT_KEY + zoneId
//    static final String ZONE_SEAT_KEY = "ZONE_SEAT_KEY_"
    // ZONE_SEAT_FIELD + seatColumnName + seatIndex
//    static final String ZONE_SEAT_FIELD = "SEAT_COLUMN_NAME_"

    static final String MUTEX_LOCK = "MUTEX_LOCK_"
    static final String MUTEX_LOCK_ON_LOCK = "1"
//    static final String MUTEX_LOCK_OFF_LOCK = "0"

    // ZONE_SEAT_KEY + zoneId : {
    //      ZONE_SEAT_FIELD_ROW_INDEX + rowIndex + ZONE_SEAT_FIELD_INTERPOINT + ZONE_SEAT_FIELD_COLUMN_INDEX + colIndex : status
    //      rowIndex + ZONE_SEAT_FIELD_INTERPOINT + colIndex : status
    //      }
    // ZONE:01 = {
    //      ROW:01_COL:01 = 0
    //      01_01 = 0
    // }
    static final String ZONE_SEAT_KEY_PREFIX = "ZONE:"
    static final String ZONE_SEAT_FIELD_ROW_PREFIX =  "ROW:"
    static final String ZONE_SEAT_FIELD_COLUMN_PREFIX = "COL:"
    static final String ZONE_SEAT_FIELD_INTERPOINT = "_"
    static final String ZONE_SEAT_VALUE_DISABLED = Byte.MIN_VALUE
    static final String ZONE_SEAT_VALUE_EMPTY = 0
    static final String ZONE_SEAT_VALUE_RESERVED = 1
//    static final String ZONE_SEAT_VALUE_ = ""

    static final String INDEX_ZONE_KEY_PREFIX = "INDEX:ZONE:"
//    static final String SELECTOR_USR_KEY_PREFIX = "SELECTOR:USR:"
    static final String OCCUPY_USR_KEY_PREFIX = "OCCUPY:USR:"
    static final String MUTEX_LOCK_ZONE_KEY_PREFIX = "MUTEX_LOCK_ZONE:"


    static final String INDEX_ZONE_TEMP_KEY = "TEMP:INDEX:ZONE:"



    @Autowired
    RedisService redisService

    def deleteKey(String key){
        redisService.withRedis { Jedis jedis ->
            jedis.del(key)
        }
    }


    def setMutexLock(String lockKey, String value){
        redisService.withRedis { Jedis jedis ->
            return jedis.set(lockKey, value, "NX", "EX", 10)
        }
    }
    def releaseMutexLock(String lockKey, String value){
        redisService.withRedis {Jedis jedis ->
            if(jedis.get(lockKey) == value) deleteKey(lockKey)
        }
    }

    def withLock(String lockKey, Closure closure){
        String value = UUID.randomUUID()
        try{
            while(setMutexLock(lockKey, value) != 0){
                Thread.sleep(50)
            }
            closure()
        }catch(Exception e){
            e.printStackTrace()
        }finally{
            releaseMutexLock(lockKey, value)
        }
    }



    Map<String, String> setZoneSeats(Zone zone, Set<List<Integer>> disableSeat){
        String zoneKey = ZONE_SEAT_KEY_PREFIX + zone.id
        String zoneIndexKey = INDEX_ZONE_KEY_PREFIX + zone.id

        Map<String, String> map = [:]

        String lockKey = MUTEX_LOCK_ZONE_KEY_PREFIX + zone.id

        withLock(lockKey, {
            redisService.withTransaction { Transaction transaction ->
//            transaction.hmset(zoneKey, map as Map<String, String>)
                for (i in 0..<zone.rowCount) {
                    for (j in 0..<zone.columnCount) {
                        String fieldName = ZONE_SEAT_FIELD_ROW_PREFIX + i + ZONE_SEAT_FIELD_INTERPOINT + ZONE_SEAT_FIELD_COLUMN_PREFIX + j
                        String fieldValue = ZONE_SEAT_VALUE_EMPTY
                        if([i,j] in disableSeat) fieldValue = ZONE_SEAT_VALUE_DISABLED

                        map.put(fieldName, fieldValue)

                        transaction.hset(zoneKey, fieldName, fieldValue)
                        transaction.zadd(zoneIndexKey, Integer.valueOf(fieldValue), fieldName)
                    }
                }
            }
        })
        return map
    }
    Map<String, String> getZoneSeats(Zone zone){
        String zoneKey = ZONE_SEAT_KEY_PREFIX + zone.id
        Map<String, String> map = [:]
        redisService.withRedis { Jedis jedis ->
            map = jedis.hgetAll(zoneKey)
        }
        return map
    }

    Long countZoneEmptySeat(Long zoneId){
        Long count = 0

        redisService.withRedis { Jedis redis ->
            count = redis.zcount(INDEX_ZONE_KEY_PREFIX + zoneId, ZONE_SEAT_VALUE_EMPTY, ZONE_SEAT_VALUE_EMPTY)
        }
        return count
    }
//    Long countEventEmptySeat(Event event){
//        return event.zones.count {
//            countZoneEmptySeat(it.id)
//        }
//    }

    Set<String> getZoneEmptySeats(Long zoneId){
        def emptySeat = []

        String lockKey = MUTEX_LOCK_ZONE_KEY_PREFIX + zoneId
        withLock(lockKey, {
            redisService.withRedis { Jedis redis ->
                emptySeat = redis.zrangeByScore(INDEX_ZONE_KEY_PREFIX + zoneId, ZONE_SEAT_VALUE_EMPTY, ZONE_SEAT_VALUE_EMPTY)
            }
        })
        return emptySeat
    }


    def setUserOccupySeat(Seat oldSeat, Seat newSeat) {
        String occupyUserKey = OCCUPY_USR_KEY_PREFIX + oldSeat.userId

        String occupyUserOldValue = ZONE_SEAT_FIELD_ROW_PREFIX + oldSeat.rowIndex + ZONE_SEAT_FIELD_INTERPOINT + ZONE_SEAT_FIELD_COLUMN_PREFIX + oldSeat.columnIndex
        String occupyUserNewValue = ZONE_SEAT_FIELD_ROW_PREFIX + newSeat.rowIndex + ZONE_SEAT_FIELD_INTERPOINT + ZONE_SEAT_FIELD_COLUMN_PREFIX + newSeat.columnIndex

        String indexKey = INDEX_ZONE_KEY_PREFIX + oldSeat.zoneId
        String indexOldField = ZONE_SEAT_FIELD_ROW_PREFIX + oldSeat.rowIndex + ZONE_SEAT_FIELD_INTERPOINT + ZONE_SEAT_FIELD_COLUMN_PREFIX + oldSeat.columnIndex
        String indexNewField = ZONE_SEAT_FIELD_ROW_PREFIX + newSeat.rowIndex + ZONE_SEAT_FIELD_INTERPOINT + ZONE_SEAT_FIELD_COLUMN_PREFIX + newSeat.columnIndex

        String lockKey = MUTEX_LOCK_ZONE_KEY_PREFIX + oldSeat.zoneId
        withLock(lockKey, {
            redisService.withRedis { Jedis jedis ->

            }
        })
    }





    def test(Long zoneId, int num){
        String zoneKey = ZONE_SEAT_KEY_PREFIX + zoneId
        redisService.withRedis { Jedis jedis ->
        }

    }



}
