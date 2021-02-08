package selectseat

import org.springframework.beans.factory.annotation.Autowired
import selectseat.redis.SelectSeatRedisService
import selectseat.utility.ToolService

import javax.persistence.Transient

class Zone {
    static final CODE_PREFIX = "ZO"

    Map<String, String>seats
    def selectSeatRedisService
    static transients = ['seats', 'selectSeatRedisService']


    Long id
    Long version
    String name
    int columnCount
    int rowCount
    int totalSeat
    String zoneCode

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

//    def afterLoad(){
//    }


    String getHashId(){
        return ToolService.encodeHashid(Zone, id)
    }

    Map<String, String> getSeats(){
        this.seats = selectSeatRedisService.getZoneSeats(this)
    }
//    void setSeats(Map<String, String> redisSeats){
//        this.seats = redisSeats
//    }




}
