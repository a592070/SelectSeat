package selectseat

import grails.gorm.transactions.Transactional
import selectseat.annotation.SeatServiceAspect

@Transactional
class SeatService {

    def serviceMethod() {
    }

    @SeatServiceAspect
    List<Seat> getColumnSeat(String seatCode){
        println "SeatService.getColumnSeat "+seatCode
        return SeatMap.findBySeatCode(seatCode).seats
    }
    List<Seat> getColumnSeatByZone(String zoneCode, String columnName){
        String seatCode = "${zoneCode}S${columnName}"
        return getColumnSeat(seatCode)
    }
}
