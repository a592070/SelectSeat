package selectseat

class Zone {
/* Default (injected) attributes of GORM */
    Long id
    Long version

    String name
    int  number


    static belongsTo = [event:Event]

    static constraints = {
        name nullable: false
        number nullable: false

    }
}
