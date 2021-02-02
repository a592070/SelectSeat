package booking

import grails.gorm.transactions.Transactional
import org.grails.datastore.mapping.query.api.BuildableCriteria
import org.hibernate.transform.Transformers
import selectseat.Event
import selectseat.Ticket
import selectseat.Zone

@Transactional
class BookingService {

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

}
