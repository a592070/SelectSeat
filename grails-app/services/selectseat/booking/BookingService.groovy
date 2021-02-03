package selectseat.booking

import grails.gorm.transactions.Transactional
import org.springframework.stereotype.Component
import selectseat.Event
import selectseat.Zone
import selectseat.annotation.QueryEmptySeatAspect

@Transactional
class BookingService {

    def seatServiceAspect

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

    @QueryEmptySeatAspect
    def searchEventZone(String eventId){
//        def zoneList = Zone.createCriteria().list {
//            eq("$event", "${eventId}")
//        }

//        def zoneList = Zone.findByEvent(Event.get(eventId))
        def zoneList = Zone.findAllByEvent(Event.get(eventId))
//        def zoneList = []

        return [zoneList: zoneList]

    }


//    def beforeInterceptor = [action: this.&auth, except: 'searchEventZone']
//// defined with private scope, so it's not considered an action
//    private auth() {
//        println "**********************"
//    }

}
