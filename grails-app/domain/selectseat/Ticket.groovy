package selectseat

import grails.compiler.GrailsCompileStatic

@GrailsCompileStatic
class Ticket {
/* Default (injected) attributes of GORM */
    Long id
    Long version

    String type
    int stock
    int price

    final static String TYPE_ADULT_TICKET = "全票"
    final static String TYPE_HALF_TICKET = "半票"


    static belongsTo = [event: Event]

    static constraints = {
        type nullable: false, blank: false
        stock nullable: false, blank: false
        price nullable: false, blank: false
    }
}
