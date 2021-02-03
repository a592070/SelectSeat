package selectseat

import grails.plugins.redis.RedisService

class SeatMap {
    Long id
    Long version

    String columnName
    int rowAmount
    String seatCode
    Zone zone

    List<Seat> seats = []

    static belongsTo = [zone:Zone]
    static hasMany = [seats: Seat]

    static final String CODE_PREFIX = "S"

    transient RedisService redisService
    transient String REDIS_KEY_SEAT_MAP

    static constraints = {
        columnName nullable: false
        rowAmount nullable: false
        seatCode nullable: true, unique: true
    }
    static mapping = {
        seats cascade: "all"
    }

    def beforeInsert(){
        for (i in 0..<rowAmount) {
            this.addToSeats(new Seat())
        }

        seatCode = zone.zoneCode + CODE_PREFIX + columnName
    }
//    def afterInsert(){
//        updateRedisKeySeatMap()
//    }
//    def afterUpdate(){
//        updateRedisKeySeatMap()
//    }
//    def onLoad() {
//        println "before load"
//    }
//    def afterLoad(){
//        println "after load"
//        updateRedisKeySeatMap()
//    }
//    def updateRedisKeySeatMap(){
//        REDIS_KEY_SEAT_MAP = seatCode
//    }
}
