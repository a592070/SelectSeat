package selectseat

class Zone {
/* Default (injected) attributes of GORM */
    Long id
    Long version

    String name

    def number = {
        int num
        seats.each {num += it.rowAmount}
        return num
    }

    static belongsTo = [event: Event]
    static hasMany = [seats: Seat]

    static constraints = {
        name nullable: false
//        number nullable: false
    }
}
