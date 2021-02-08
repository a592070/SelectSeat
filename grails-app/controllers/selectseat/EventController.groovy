package selectseat

import grails.converters.JSON
import org.grails.web.json.JSONArray
import org.springframework.beans.factory.annotation.Autowire
import selectseat.booking.BookingService
import selectseat.redis.SelectSeatRedisService
import selectseat.utility.ToolService
import selectseat.utils.StringUtils


class EventController {
    EventService eventService
    ToolService toolService
    BookingService bookingService

//    static scaffold = Event
//    def saveL(String query) {
//        def location = new Location(name: query)
//        location.save()
//        render 'SUCCESS!'
//    }

    def create() {
        def locationList = Location.findAll()
        return [locationList: locationList]

    }


    def createEvent() {
        def beginDate = params.date('beginDate', 'MM/dd/yyyy')
        def endDate = params.date('endDate', 'MM/dd/yyyy')
        def totalSeat = params.int('columnCount')*params.int('rowCount')

        def clickRecord = params.clickRecord
        def disabledSeat = JSON.parse(clickRecord) as Set
        disabledSeat.each {it ->
            it as List<Integer>
        }
//        println disabledSeat

        def event = eventService.saveEvent(params.eventName.toString(), beginDate, endDate, params.long('location'))
        def zone = eventService.saveZone(params.zone.toString(), params.int('columnCount'), params.int('rowCount'), totalSeat, event.id, disabledSeat)
        def seats = zone.getSeats()
//        println('seat='+seats)
        def map2List = toolService.seatMap2List(seats, params.int('rowCount'), params.int('columnCount'))
        render (view: 'saveL' ,model: [map2List: map2List] )
    }

}
