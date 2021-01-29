package selectseat

import groovy.transform.CompileStatic

@CompileStatic
class TicketController {

//    static scaffold = Ticket

    static allowedMethods = [index: 'GET', show: 'GET']
    TicketBookingService ticketBookingService

    def index() {
        [ticketList: ticketBookingService.findAll()]
    }

    def show(Long id){
        [ticketDetial: ticketBookingService.findById(id)]
    }
}
