package selectseat

import grails.gorm.services.Service
import grails.gorm.transactions.Transactional
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


    @Autowired
    RedisService redisService

    def serviceMethod() {
    }

    def existEmptySeat(Long eventId){
        redisService.withRedis { Jedis jedis ->
            return jedis.exists(EMPTY_SEAT_KEY+eventId)
        }
    }
    def getEmptySeat(Long eventId){
        redisService.withRedis { Jedis jedis ->
            return jedis.get(EMPTY_SEAT_KEY+eventId)
        }
    }
    def setEmptySeat(Long eventId, int emptySeat){
        redisService.withRedis { Jedis jedis ->
            return jedis.set(EMPTY_SEAT_KEY+eventId, emptySeat.toString(), "NX", "EX", 10*60)
        }
    }

}
