package redis

import selectseat.Event
import selectseat.Zone

class ZoneSeat {

    Event event
    Zone zone
    // bitmap: 每一排的座位
//    def columnSeat = []

    // 每一列的座位代號 代號是映射到每一排座位的key值
    def seatMap = []

    static mapWith = "none"

    ZoneSeat() {
    }
    ZoneSeat(Event event, Zone zone) {
        this.event = event
        this.zone = zone
        zone.rowNumber.each {
            def columnSeat = []
            zone.columnNumber.each {columnSeat.add(true)}
            seatMap.add(it: columnSeat)
        }
    }

    static constraints = {
    }
}
