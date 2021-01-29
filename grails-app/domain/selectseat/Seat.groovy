package selectseat

import grails.plugins.redis.RedisService
import utility.ToolService

class Seat {
/* Default (injected) attributes of GORM */
    Long id
    Long version

    // [0,0,0,0,0]
    List<Integer> seatBitmap

    String columnName
    int rowAmount
    String seatCode

    static belongsTo = [zone:Zone]

    static final String CODE_PREFIX = "S"

    transient RedisService redisService
    transient String REDIS_KEY_SEAT_MAP

    static constraints = {
        columnName nullable: false
        rowAmount nullable: false
        seatCode unique: true
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
        seatBitmap = seatList

//        def tmpNo = CODE_PREFIX + zoneId + columnName + ToolService.generateRandomWord(5,true)
//        while(Seat.countBySeatCode(tmpNo) ){
//            tmpNo = CODE_PREFIX + zoneId + columnName +ToolService.generateRandomWord(5,true)
//        }
//        seatCode = tmpNo

        seatCode = zone.zoneCode + CODE_PREFIX + columnName
    }
    def afterInsert(){
        updateRedisKeySeatMap()
    }
    def afterUpdate(){
        updateRedisKeySeatMap()
    }
    def onLoad() {
        println "before load"
//        updateRedisKeySeatMap()
    }
    def afterLoad(){
        println "after load"
        updateRedisKeySeatMap()
    }
    def updateRedisKeySeatMap(){
        REDIS_KEY_SEAT_MAP = seatCode
    }




}
