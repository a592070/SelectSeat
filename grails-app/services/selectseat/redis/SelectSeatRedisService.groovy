package selectseat.redis


import grails.gorm.services.Service
import grails.plugins.redis.RedisService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import redis.clients.jedis.Jedis
import redis.clients.jedis.JedisMonitor
import redis.clients.jedis.Pipeline
import redis.clients.jedis.Response
import redis.clients.jedis.Transaction
import selectseat.Event
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
    static final String ZONE_SEAT_VALUE_DISABLED = "DISABLED"
    static final String ZONE_SEAT_VALUE_EMPTY = "0"
    static final String ZONE_SEAT_VALUE_RESERVED = "1"
//    static final String ZONE_SEAT_VALUE_ = ""



    @Autowired
    RedisService redisService

    def deleteKey(String key){
        redisService.withRedis { Jedis jedis ->
            jedis.del(key)
        }
    }


    def setnxMutexLock(String lockKey){
        redisService.withRedis { Jedis jedis ->
            return jedis.setnx(MUTEX_LOCK+lockKey, MUTEX_LOCK_ON_LOCK)
        }
    }
    def expireMutexLock(String lockKey, int timeout){
        redisService.withRedis { Jedis jedis ->
            jedis.expire(MUTEX_LOCK+lockKey, timeout)
            jedis.subscribe()
        }
        new JedisMonitor() {
            @Override
            void onCommand(String command) {

            }
        }
    }
    def delMutexLock(String lockKey){
        deleteKey(MUTEX_LOCK+lockKey)
    }





    Map<String, String> setZoneSeats(Zone zone, Set<List<Integer>> disableSeat){
        String zoneKey = ZONE_SEAT_KEY_PREFIX + zone.id
        Map<String, String> map = [:]
        for (i in 0..<zone.rowCount) {
            for (j in 0..<zone.columnCount) {
                String fieldName = ZONE_SEAT_FIELD_ROW_PREFIX + i + ZONE_SEAT_FIELD_INTERPOINT + ZONE_SEAT_FIELD_COLUMN_PREFIX + j
                String fieldValue = ZONE_SEAT_VALUE_EMPTY
                if([i,j] in disableSeat) fieldValue = "DISABLED"
                map.put(fieldName, fieldValue)
            }
        }
        redisService.withTransaction { Transaction transaction ->
            transaction.hmset(zoneKey, map as Map<String, String>)
        }
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

    int countZoneEmptySeat(List<Long> zoneId){
        int totalEmptySeat
        List<Response<Map<String, String>>> tmp = []
        redisService.withTransaction { Transaction redis ->
            zoneId.each {
                tmp << redis.hgetAll(ZONE_SEAT_KEY_PREFIX + it)
            }
        }
        tmp.each {
            totalEmptySeat += it.get().count {it.value == ZONE_SEAT_VALUE_EMPTY}
        }
        return totalEmptySeat
    }
    int countEventEmptySeat(Event event){
        def zoneIds = event.zones.collect {
            it.id
        }
        return countZoneEmptySeat(zoneIds)
    }

    def getZoneEmptySeat(Long zoneId){

    }


    def incrZoneSeat(Long zoneId, int rowIndex, int colIndex){
        String zoneKey = ZONE_SEAT_KEY_PREFIX + zoneId
        String fieldName = ZONE_SEAT_FIELD_ROW_PREFIX + rowIndex + ZONE_SEAT_FIELD_INTERPOINT + ZONE_SEAT_FIELD_COLUMN_PREFIX + colIndex
        String fieldName1 = ZONE_SEAT_FIELD_ROW_PREFIX + 0 + ZONE_SEAT_FIELD_INTERPOINT + ZONE_SEAT_FIELD_COLUMN_PREFIX + 2


        redisService.withRedis { Jedis redis ->

            println fieldName
            def f1 = redis.hincrBy(zoneKey, fieldName, 1)
            println f1

            println fieldName1
            def f2 = redis.hincrBy(zoneKey, fieldName1, 1)
            println f2

            if(f1 != ZONE_SEAT_VALUE_RESERVED && f2 != ZONE_SEAT_VALUE_RESERVED) throw new Exception("不符合")
        }
    }


    def test(Long zoneId, int num){
        String zoneKey = ZONE_SEAT_KEY_PREFIX + zoneId
        redisService.withRedis { Jedis jedis ->

        }

    }



}