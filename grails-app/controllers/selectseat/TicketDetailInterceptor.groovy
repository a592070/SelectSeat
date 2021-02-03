package selectseat

import com.budjb.rabbitmq.publisher.RabbitMessagePublisher
import groovy.transform.CompileStatic

@CompileStatic
class TicketDetailInterceptor {

    RabbitMessagePublisher rabbitMessagePublisher

    TicketDetailInterceptor(){
        match(controller: 'ticket', action: 'show')
    }

    boolean after() {
        final Ticket ticket = (Ticket) model.ticketDetial

        rabbitMessagePublisher.send {
            routingKey = 'seatQueue'
            body = [id:ticket.id, type: ticket.type]
        }
        true
    }

}
