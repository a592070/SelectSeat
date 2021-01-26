package selectseat

class Location {

/* Default (injected) attributes of GORM */
    Long id
    Long version

    String name

    static hasMany = [events:Event]
    static constraints = {
        name nullable: false, blank: false
    }
}
