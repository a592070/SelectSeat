package selectseat

import grails.compiler.GrailsCompileStatic
import grails.rest.Resource

@Resource
@GrailsCompileStatic
class TicketRecord {

    Long id
    Long version
    String type
    int count

    static constraints = {

        type nullable: false
        count nullable: false, min: 0
    }
}
