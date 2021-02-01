package selectseat

import annotation.SeatAspect
import grails.gorm.transactions.Transactional
import grails.plugins.redis.RedisService
import org.springframework.stereotype.Service

@Transactional
class SelectedService {

    RedisService redisService

    def serviceMethod() {
    }


    List<Integer> getSeatByZone(Long zoneId){
        return getSeatByZone(Zone.get(zoneId))
    }
    List<Integer> getSeatByZone(String zoneCode){
        return getSeatByZone(Zone.findByZoneCode(zoneCode))
    }
    List<Integer> getSeatByZone(Zone zone){
    }

//    List<Integer> getSeat(Integer seatId){
//        return Seat.get(seatId).seatBitmap
//    }

    List<Integer> getColumnSeat(String seatCode){
        println seatCode
        return Seat.findBySeatCode(seatCode).seatBitmap
    }
    List<Integer> getColumnSeat(String zoneCode, String columnName){
        String seatCode = "${zoneCode}S${columnName}"
        return getColumnSeat(seatCode)
    }





}
