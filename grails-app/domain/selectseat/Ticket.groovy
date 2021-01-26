package selectseat

class Ticket {
/* Default (injected) attributes of GORM */
    long id
    Long version

    String type
    int stock
    int price

    static belongsTo = [event: Event]

    static constraints = {
        type nullable: false
        stock nullable: false
        price nullable: false
    }
}
