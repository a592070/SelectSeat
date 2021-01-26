package selectseat

class Event {
/* Default (injected) attributes of GORM */
    long id
    Long version

    String eventCode
    String name
    Date date

//    一個 site 有多個 event
    static belongsTo = [location: Location]

    static hasMany = [tickets: Ticket]

    static constraints = {
        eventCode nullable: false, unique: true
        name nullable: false
        date nullable: false
    }
}
