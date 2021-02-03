package selectseat

import selectseat.booking.BookingService

import java.awt.print.Book

class FrontPageController {
    BookingService bookingService
    def index() { }

    def result() { }

    def eventSearch(String query){

        def eventByQuery = bookingService.searchEventsByQuery(query)


        def list = eventByQuery.eventList
        def result = eventByQuery.result
        def events = eventByQuery.totalEvents

        return [eventList:list,
                result: result,
                totalEvents: events,
                query: query
        ]
    }

    def buyTicket(String eveId){
        def event = Event.get(eveId)
        def zoneByEvent = bookingService.searchEventZone(eveId)
        def zoneList = zoneByEvent.zoneList

        return [eventResult: event, zoneList: zoneList]
    }


}
