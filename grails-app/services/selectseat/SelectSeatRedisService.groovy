package selectseat


import grails.gorm.services.Service
import grails.plugins.redis.RedisService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import redis.clients.jedis.Jedis

@Service
@Component
class SelectSeatRedisService {

    // EMPTY_SEAT_KEY + eventId
    static final String EMPTY_SEAT_KEY = "EMPTY_SEAT_KEY_"
    // ZONE_SEAT_KEY + zoneId
    static final String ZONE_SEAT_KEY = "ZONE_SEAT_KEY_"
    // ZONE_SEAT_FIELD + seatColumnName + seatIndex
    static final String ZONE_SEAT_FIELD = "SEAT_COLUMN_NAME_"

    static final String MUTEX_LOCK = "MUTEX_LOCK_"
    static final String MUTEX_LOCK_ON_LOCK = "1"
//    static final String MUTEX_LOCK_OFF_LOCK = "0"


    @Autowired
    RedisService redisService

    def deleteKey(String key){
        redisService.deleteKeysWithPattern(key)
    }

    def existEmptySeat(Long zoneId){
        redisService.withRedis { Jedis jedis ->
            return jedis.exists(EMPTY_SEAT_KEY+zoneId)
        }
    }
    def getEmptySeat(Long zoneId){
        redisService.withRedis { Jedis jedis ->
            return jedis.get(EMPTY_SEAT_KEY+zoneId)
        }
    }

    def setEmptySeat(Long zoneId, int emptySeat, int timeout=30){
        redisService.withRedis { Jedis jedis ->
//            return jedis.set(EMPTY_SEAT_KEY+zoneId, "${emptySeat}", "NX", "EX", 30)
            return jedis.setex(EMPTY_SEAT_KEY+zoneId, timeout, "${emptySeat}")
        }
    }
    def setExpireEmptySeat(Long zoneId, int timeout=30){
        redisService.withRedis { Jedis jedis ->
            jedis.expire(EMPTY_SEAT_KEY+zoneId, timeout)
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
        }
    }
    def delMutexLock(String lockKey){
        deleteKey(MUTEX_LOCK+lockKey)
    }



    def setEmptySeatMutexLock(Long zoneId){
        return setnxMutexLock(EMPTY_SEAT_KEY+zoneId)
    }
    def expireEmptySeatMutexLock(Long zoneId, int timeout = 1*60){
        expireMutexLock(EMPTY_SEAT_KEY+zoneId, timeout)
    }
    def delEmptySeatMutexLock(Long zoneId){
        delMutexLock(EMPTY_SEAT_KEY+zoneId)
    }



    def getTotalEmptySeat(Long eventId){

    }




}
