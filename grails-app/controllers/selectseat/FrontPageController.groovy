package selectseat

class FrontPageController {

    def index() { }

    def result() { }

    def eventSearch(String query){
        def eventList = Event.createCriteria().list{
            ilike "name", "%${query}%"
        }

        return [eventList:eventList,
                result: eventList.size(),
                totalEvents: Event.count()]
    }


}
