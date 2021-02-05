package selectseat

import grails.gorm.transactions.Transactional
import selectseat.redis.SelectSeatRedisService

@Transactional
class EventService {
    SelectSeatRedisService selectSeatRedisService


    def saveEvent(String name, Date beginDate, Date endDate, Long location){
        new Event (name: name, beginDate: beginDate, endDate: endDate, location: location).save()
    }

    def saveZone(String name, int columnCount, int rowCount, int totalSeat, Long event, Set<List<Integer>> disabledSeat=[]){
        Zone zone = new Zone(name: name, rowCount: rowCount, columnCount: columnCount, totalSeat: totalSeat, event: event).save()
        zone.setSeats(selectSeatRedisService.setZoneSeats(zone, disabledSeat))
        return zone
    }
}
