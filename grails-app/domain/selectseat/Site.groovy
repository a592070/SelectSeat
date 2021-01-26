package selectseat

class Site {

/* Default (injected) attributes of GORM */
    long id
    Long version

    String name

    static hasMany = [events: Event]


    static constraints = {
        name nullable: false
    }
}
