package selectseat


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

        def event = eventService.saveEvent(params.eventName.toString(), beginDate, endDate, params.long('location'))
        eventService.saveZone(params.zone.toString(), params.int('columnCount'), params.int('rowCount'), totalSeat, event.id)
        render 'SUCCESS!'
    }
}
