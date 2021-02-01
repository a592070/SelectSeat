package selectseat

import utility.ToolService

class Zone {
/* Default (injected) attributes of GORM */
    Long id
    Long version

    String name
    int number

    String zoneCode
    static final CODE_PREFIX = "Z"

    static belongsTo = [event: Event]
    static hasMany = [seatMap: SeatMap]

    static constraints = {
        name nullable: false
        number nullable: false
        zoneCode nullable: true, unique: true
    }

    def beforeInsert(){
        def tmpNo = event.eventCode + CODE_PREFIX + ToolService.generateRandomWord(5,true)
        while(Zone.countByZoneCode(tmpNo) ){
            tmpNo = event.eventCode + CODE_PREFIX +ToolService.generateRandomWord(5,true)
        }
        this.zoneCode = tmpNo
    }


}
