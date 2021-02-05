package selectseat

import org.springframework.context.MessageSource
import selectseat.utility.ToolService

class Event {
/* Default (injected) attributes of GORM */
    Long id
    Long version

    String eventCode
    String name
    Date beginDate
    Date endDate
    Location location
//    Set tickets

    // begin time
    // end time
    // def displayDate  // 2021/01/01 07:00~09:00
    // hashId
    // def totalEmptySeat() // count by zone.emptySeat


//    一個 site 有多個 event
    static belongsTo = [location: Location]

    static hasMany = [tickets: Ticket]

    // gorm redis-gorm 開啟時需要額外標註map類型
//    static mapWith = "hibernate"
    static final String CODE_PREFIX = "EV"

    static constraints = {
        eventCode nullable: true, unique: true
        name nullable: false
        beginDate nullable: true
        endDate nullable: true
    }

    def beforeInsert(){
        def tmpNo = CODE_PREFIX + ToolService.generateRandomWord(5,true)
        while(Event.countByEventCode(tmpNo) ){
            tmpNo = CODE_PREFIX +ToolService.generateRandomWord(5,true)
        }
        this.eventCode = tmpNo
    }

    String getDisplayDate(){
        String sBeginDate = beginDate.format("yyyy/MM/dd/ HH:mm")
        String sEndDate = endDate.format("yyyy/MM/dd/ HH:mm")
        return sBeginDate + " - " + sEndDate.substring(sEndDate.lastIndexOf(" ")+1)
    }
    String getHashId(){
        return ToolService.encodeHashid(Event, id)
    }
    Long getTotalEmptySeat(){
        return 0
    }

}
