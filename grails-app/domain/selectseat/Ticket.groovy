package selectseat

class Ticket {
/* Default (injected) attributes of GORM */
    Long id
    Long version

    String type
    int stock
    int price

    static belongsTo = [event: Event]

    static constraints = {
        type nullable: false, blank: false
        stock nullable: false, blank: false
        price nullable: false, blank: false
    }
}
