package selectseat

import selectseat.redis.SelectSeatRedisService
import selectseat.utility.ToolService

class ZoneController {
    SelectSeatRedisService selectSeatRedisService
    ToolService toolService
//    static scaffold = Zone
    def index() {
        def zoneList = Zone.findAll()
        return [zoneList:zoneList]
        //TODO: zone 的查詢
    }

    def zoneDetail(Long zoneId){
        println('====='+zoneId)
        def zone = Zone.findById(zoneId)
        def emptySeat = selectSeatRedisService.countZoneEmptySeat(zoneId)
        def seatList = toolService.seatMap2List(zone.getSeats(), zone.getRowCount(), zone.getColumnCount())
        return  [zone:zone, emptySeat:emptySeat, seatList:seatList]

    }
}
