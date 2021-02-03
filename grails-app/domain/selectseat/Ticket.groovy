package selectseat

import grails.compiler.GrailsCompileStatic

@GrailsCompileStatic
class Ticket {
/* Default (injected) attributes of GORM */
    Long id
    Long version

    String type
    int stock //移除
    int price

    final static String TYPE_ADULT_TICKET = "ADULT"
    final static String TYPE_HALF_TICKET = "HALF"


    static belongsTo = [event: Event]

    static constraints = {
        type nullable: false, blank: false
        stock nullable: false, blank: false
        price nullable: false, blank: false
    }

    // using i18n
    String getTypeName(String locale='*'){
        if(type == TYPE_ADULT_TICKET){
            return "全票"
        }
    }
}
