package selectseat

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

    static constraints = {
        eventCode nullable: false, unique: true
        name nullable: false
        date nullable: false
    }
}
