package utility

import grails.gorm.transactions.Transactional
import grails.plugins.redis.RedisService

@Transactional
class SelectSeatRedisService {
    static RedisService redisService

//    def eventId
//    def zoneId
//    final static String REDIS_KEY_SEAT_MAP = "event:${eventId}:zone:${zoneId}"
//    final static String REDIS_KEY_SEAT = "event:${eventId}:zone:${zoneId}"



    def serviceMethod() {
    }

    Map<String, List<Integer>> getSeatMap(int eventId, int zoneId){
        String REDIS_KEY_SEAT_MAP = "event:${eventId}:zone:${zoneId}"
        return redisService.get(REDIS_KEY_SEAT_MAP)
    }
    void setSeatMap(int eventId, int zoneId, Map<String, List<Integer>> seats){
        String REDIS_KEY_SEAT_MAP = "event:${eventId}:zone:${zoneId}"
        redisService.set(REDIS_KEY_SEAT_MAP, seats)
    }

}
