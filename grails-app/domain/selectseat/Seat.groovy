package selectseat

import grails.plugins.redis.RedisService
import utility.ToolService

class Seat {
/* Default (injected) attributes of GORM */
    Long id
    Long version

    String seatBitmap = ""
    String columnName
    int rowAmount

    String seatCode = "S00000"

    static belongsTo = [zone:Zone]

    static final String CODE_PREFIX = "S"

    transient RedisService redisService
    static transient String REDIS_KEY_SEAT_MAP = "event:0:zone:0:seat:0"

    static constraints = {
        seatBitmap nullable: false
        columnName nullable: false
        rowAmount nullable: false
        seatCode nullable: false, unique: true
    }

//    def getSeatBitMap(){
//        redisService.set(REDIS_KEY_SEAT_MAP, seatBitmap)
//    }

    def beforeInsert(){
        println("====Seat beforeInsert====")
        def seatList = []
        for (i in 0..<rowAmount) {
            seatList << 0
        }
        seatBitmap = seatList as String
        println seatBitmap
        def tmpNo = CODE_PREFIX + ToolService.generateRandomWord(5,true)
        while(Seat.countBySeatCode(tmpNo) ){
            tmpNo = CODE_PREFIX +ToolService.generateRandomWord(5,true)
        }
        seatCode = tmpNo
        println(seatCode)
    }
    def afterInsert(){
        updateRedisKeySeatMap()
    }
    def afterUpdate(){
        updateRedisKeySeatMap()
    }
    def updateRedisKeySeatMap(){
        REDIS_KEY_SEAT_MAP = "event:${zone.eventId}:zone:${zoneId}:seat:${id}"
    }


}
