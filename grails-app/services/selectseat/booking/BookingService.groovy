package selectseat.booking

import grails.gorm.services.Query
import grails.gorm.services.Service
import grails.gorm.transactions.Transactional
import groovy.transform.CompileStatic
import org.springframework.stereotype.Component
import selectseat.Event
import selectseat.Seat
import selectseat.SeatMap
import selectseat.Zone
import selectseat.annotation.QueryEmptySeatAspect

//@CompileStatic
//interface IBookingService{
//
//    @Query("select count(1) from ${Seat} s, ${SeatMap} m where s.seatMap.id = m.id and s.status = 0 and m.zone.id = ${zoneId} group by m.zone.id")
//    Number countSeat(Long zoneId)
//
//    @QueryEmptySeatAspect
//    def countEmptySeat(Long zoneId)
//}

@Transactional
@Service
class BookingService/* implements IBookingService*/{
    def zoneService

    def searchEventsByQuery(String query) {
        def eventList = Event.createCriteria().list {
            like "name", "%${query}%"
        }
        return [eventList:eventList,
                result: eventList.size(),
                totalEvents: Event.count(),
                query: query
        ]
    }

    def searchEventZone(String eventId){
//        def zoneList = Zone.createCriteria().list {
//            eq("$event", "${eventId}")
//        }

//        def zoneList = Zone.findByEvent(Event.get(eventId))
        def zoneList = Zone.findAllByEvent(Event.get(eventId))

        return [zoneList: zoneList]

    }

    @QueryEmptySeatAspect
    def countEmptySeat(Long zoneId) {
        println this.getClass().getName()
        def seat = zoneService.countSeat(zoneId)
        return seat
    }
}
