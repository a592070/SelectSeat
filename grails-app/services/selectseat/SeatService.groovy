package selectseat

import grails.gorm.transactions.Transactional

@Transactional
class SeatService {

    def serviceMethod() {
    }

    List<Seat> getColumnSeat(String seatCode){
        println "SeatService.getColumnSeat "+seatCode
        return SeatMap.findBySeatCode(seatCode).seats
    }
//    List<Seat> getSeats(Long zoneId, String columnName){
//        String seatCode = "${zoneCode}S${columnName}"
//        return getColumnSeat(seatCode)
//    }
//    List<Seat> getSeats(String seatCode){
//        return getColumnSeat(seatCode)
//    }
}
