package selectseat

import selectseat.utility.ToolService

class Zone {
/* Default (injected) attributes of GORM */
    Long id
    Long version

    String name

    int columnCount
    int rowCount
    int totalSeat

    // column count
    // row count
    // total seat in zone
    // def emptySeat()
    //


    String zoneCode
    static final CODE_PREFIX = "ZO"

    Event event
    static belongsTo = [event: Event]

    static constraints = {
        name nullable: false
        zoneCode nullable: true, unique: true
    }

    def beforeInsert(){
        def tmpNo = event.eventCode + CODE_PREFIX + ToolService.generateRandomWord(5,true)
        while(Zone.countByZoneCode(tmpNo) ){
            tmpNo = event.eventCode + CODE_PREFIX +ToolService.generateRandomWord(5,true)
        }
        this.zoneCode = tmpNo
    }

    int getEmptySeat(){
        return 0
    }
    String getHashId(){
        return ""
    }




}
