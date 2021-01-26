package selectseat

class Area {
/* Default (injected) attributes of GORM */
    long id
    Long version

    String name
    int  number

    Event event

    static constraints = {
        name nullable: false
        number nullable: false
    }
}
