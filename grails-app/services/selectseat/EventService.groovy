package selectseat

import grails.gorm.transactions.Transactional

@Transactional
class EventService {

    def saveEvent(String name, Date beginDate, Date endDate, Long location){
        new Event (name: name, beginDate: beginDate, endDate: endDate, location: location).save()
    }

    def saveZone(String name, int columnCount, int rowCount, int totalSeat, Long event){
        new Zone(name: name, rowCount: rowCount, columnCount: columnCount, totalSeat: totalSeat, event: event).save()
    }
}
