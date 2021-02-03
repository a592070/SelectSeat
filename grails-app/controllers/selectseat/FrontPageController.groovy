package selectseat

import selectseat.annotation.QueryEmptySeatAspect
import selectseat.booking.BookingService

import java.awt.print.Book

class FrontPageController {
    BookingService bookingService
    def index() { }

    def result() { }

    def eventSearch(String query){

        def eventByQuery = bookingService.searchEventsByQuery(query)

        def eventList = eventByQuery.eventList
        def result = eventByQuery.result
        def events = eventByQuery.totalEvents

        return [eventList:eventList,
                result: result,
                totalEvents: events,
                query: query
        ]
    }

    def checkLogin(String eveId){
        println eveId
        def event = eveId
        render(view: 'byTicketLogin', model: [eventId:event])
    }


    def countUserTicketByEve(String user, Long eventId){
        def byEvent = bookingService.countUserTicketByEvent(user, eventId)
        def ticketNum = byEvent.ticketNum
        def exiting = bookingService.ifUserExiting(user)
        def event = Event.get(eventId)
        def zoneByEvent = bookingService.searchEventZone(eventId)
        def zoneList = zoneByEvent.zoneList
//        return [ticketNum: ticketNum, exiting: exiting]
        render(view: 'buyTicket', model: [ticketNum: ticketNum, exiting: exiting,eventResult: event, zoneList: zoneList])
    }

    def buyTicket(String eveId){
        println eveId
        def event = Event.get(eveId)
        def zoneByEvent = bookingService.searchEventZone(eveId)
        def zoneList = zoneByEvent.zoneList
        return [eventResult: event, zoneList: zoneList]
    }

    def countEmptySeat(Long zoneId){
        def emptySeat = bookingService.countEmptySeat(zoneId)

        render(emptySeat: emptySeat)
    }

}
