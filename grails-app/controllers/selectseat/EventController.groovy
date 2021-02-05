package selectseat

import grails.converters.JSON
import org.grails.web.json.JSONArray
import selectseat.redis.SelectSeatRedisService
import selectseat.utils.StringUtils


class EventController {
    EventService eventService

//    static scaffold = Event
    def saveL(String query) {
        def location = new Location(name: query)
        location.save()
        render 'SUCCESS!'
    }

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
        println disabledSeat

        def event = eventService.saveEvent(params.eventName.toString(), beginDate, endDate, params.long('location'))
        def zone = eventService.saveZone(params.zone.toString(), params.int('columnCount'), params.int('rowCount'), totalSeat, event.id, disabledSeat)
        render zone.getSeats()
    }
}
