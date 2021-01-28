package selectseat

import utility.ToolService

class Event {
/* Default (injected) attributes of GORM */
    Long id
    Long version

    String eventCode
    String name
    Date date

//    一個 site 有多個 event
    static belongsTo = [location: Location]

    static hasMany = [tickets: Ticket]

    // gorm redis-gorm 開啟時需要額外標註map類型
//    static mapWith = "hibernate"
    static final String CODE_PREFIX = "E"

    static constraints = {
        eventCode nullable: true, unique: true
        name nullable: false
        date nullable: true
    }

    def beforeInsert(){
        def tmpNo = CODE_PREFIX + ToolService.generateRandomWord(5,true)
        while(Event.countByEventCode(tmpNo) ){
            tmpNo = CODE_PREFIX +ToolService.generateRandomWord(5,true)
        }
        this.eventCode = tmpNo
    }

}
