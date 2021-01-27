package selectseat

import utility.ToolService

class Seat {
/* Default (injected) attributes of GORM */
    Long id
    Long version

    String seatBitmap
    String columnName
    int rowAmount

    String seatCode

    static belongsTo = [zone:Zone]

    static final String CODE_PREFIX = "S"

    transient redisService
    static String REDIS_KEY_SEAT_MAP = "event:${zone.eventId}:zone:${zoneId}"

    static constraints = {
        seatBitmap nullable: false
        columnName nullable: false
        rowAmount nullable: false
    }

//    def getSeatBitMap(){
//        redisService.set(REDIS_KEY_SEAT_MAP, seatBitmap)
//    }

    def beforeInsert(){
        seatBitmap = "0"*rowAmount
        def tmpNo = CODE_PREFIX + ToolService.generateRandomWord(5,true)
        while(OrderList.countByOrderCode(tmpNo) ){
            tmpNo = CODE_PREFIX +ToolService.generateRandomWord(5,true)
        }
        this.orderCode = tmpNo
    }


}
