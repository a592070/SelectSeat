package selectseat

import grails.compiler.GrailsCompileStatic
import org.grails.plugins.localization.Localization

@GrailsCompileStatic
class Ticket {
/* Default (injected) attributes of GORM */
    Long id
    Long version

    String type
    int price

    final static String TYPE_ADULT_TICKET = "ADULT"
    final static String TYPE_CONCESSION_TICKET = "CONCESSION"
    final static String TYPE_OTHER_TICKET = "OTHER"

    Event event
    static belongsTo = [event: Event]

    static constraints = {
        type nullable: false, blank: false
        price nullable: false, blank: false
    }

    // using i18n
    String getTypeName(String locale='*'){
        return Localization.getMessage("ticket.type."+type, locale)
    }
}
