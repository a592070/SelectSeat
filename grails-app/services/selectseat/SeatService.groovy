package selectseat

import grails.gorm.transactions.Transactional

@Transactional
class SeatService {

    def serviceMethod() {
    }

    List<Seat> getColumnSeat(String seatCode){
        println seatCode
        return SeatMap.findBySeatCode(seatCode).seats
    }
    List<Seat> getColumnSeat(String zoneCode, String columnName){
        String seatCode = "${zoneCode}S${columnName}"
        return getColumnSeat(seatCode)
    }
}
