package selectseat

class Zone {
/* Default (injected) attributes of GORM */
    Long id
    Long version

    String name
    int number

    String zoneCode
    static final CODE_PREFIX = "Z"

    static belongsTo = [event: Event]
    static hasMany = [seats: Seat]

    static constraints = {
        name nullable: false
        number nullable: false
        zoneCode nullable: true, unique: true
    }

    def beforeInsert(){
        zoneCode = event.eventCode + CODE_PREFIX + (Zone.countByEvent(event)+1)
    }


}
