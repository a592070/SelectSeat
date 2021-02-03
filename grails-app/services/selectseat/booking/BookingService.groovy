package selectseat.booking

import grails.gorm.services.Query
import grails.gorm.services.Service
import grails.gorm.transactions.Transactional
import groovy.transform.CompileStatic
import org.springframework.beans.factory.annotation.Autowire
import org.springframework.stereotype.Component
import selectseat.Event
import selectseat.ILonginCheckService
import selectseat.LonginCheckService
import selectseat.Seat
import selectseat.SeatMap
import selectseat.User
import selectseat.Zone
import selectseat.ZoneService
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
class BookingService/* implements IBookingService*/{

    def zoneService
    def longinCheckService
    def orderListService


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

    def searchEventZone(Long eventId){
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
        return [emptySeat: seat]
    }

    def countUserTicketByEvent(String query, Long eventId){
        def userId = longinCheckService.searchUser(query)
        println(userId)
        def ticketNum = orderListService.countTicket(userId, eventId)
        return [ticketNum: ticketNum]
    }

    def ifUserExiting(String query){
        def userResult = User.where{email =~ query}.get()
        if(userResult){
            return [userStatus: '歡迎 '+ query]
        }else{
            return [userStatus: '使用者不存在']
        }
    }
}
