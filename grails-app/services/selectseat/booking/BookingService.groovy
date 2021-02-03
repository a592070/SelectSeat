package selectseat.booking

import grails.gorm.services.Query
import grails.gorm.services.Service
import grails.gorm.transactions.Transactional
import groovy.transform.CompileStatic
import selectseat.Event
import selectseat.Seat
import selectseat.SeatMap
import selectseat.Zone

@CompileStatic
interface IBookingService{

    @Query("select count(1) from ${Seat} s, ${SeatMap} m where s.seatMap.id = m.id and s.status = 0 and m.zone.id = ${zone} group by m.zone.id")
    Number countSeat(Long zone)
}

@Transactional
@Service(SeatMap)
abstract class BookingService implements IBookingService{

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

   def countEmptySeat(Long zone) {
       def seat = countSeat(zone)

       return [emptySeat: seat]
   }
}
