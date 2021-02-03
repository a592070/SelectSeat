package selectseat

import grails.compiler.GrailsCompileStatic

@GrailsCompileStatic
class Ticket {
/* Default (injected) attributes of GORM */
    Long id
    Long version

    String type
    int price

    final static String TYPE_ADULT_TICKET = "ADULT"
    final static String TYPE_HALF_TICKET = "HALF"
    final static String TYPE_OTHER_TICKET = "OTHER"

    Event event
    static belongsTo = [event: Event]

    static constraints = {
        type nullable: false, blank: false
        price nullable: false, blank: false
    }

    // using i18n
    String getTypeName(String locale='*'){
        switch (type){
            case TYPE_ADULT_TICKET:
                return "全票"
            case TYPE_HALF_TICKET:
                return "半票"
            default:
                return "其他"
        }
    }
}
