package utility

import grails.gorm.transactions.Transactional
import grails.plugins.redis.RedisService
import selectseat.Zone

@Transactional
class SelectSeatRedisService {
    static RedisService redisService

//    def eventId
//    def zoneId

    def serviceMethod() {
    }


    Map<String, List<Integer>> getSeatsMap(Zone zone){

        return redisService.get(REDIS_KEY_SEAT_MAP)
    }
    void setSeatMap(int eventId, int zoneId, Map<String, List<Integer>> seats){
        String REDIS_KEY_SEAT_MAP = "event:${eventId}:zone:${zoneId}"
        redisService.set(REDIS_KEY_SEAT_MAP, seats)
    }

}
