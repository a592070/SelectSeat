package selectseat

import com.budjb.rabbitmq.RabbitContext
import com.budjb.rabbitmq.consumer.MessageConsumer
import com.budjb.rabbitmq.consumer.MessageConsumerEventHandler
import com.budjb.rabbitmq.consumer.RabbitMessageHandler
import com.budjb.rabbitmq.publisher.RabbitMessagePublisher
import com.budjb.rabbitmq.queuebuilder.QueueBuilder
import com.budjb.rabbitmq.queuebuilder.QueueBuilderImpl
import grails.gorm.transactions.Transactional
import org.aspectj.lang.annotation.Aspect
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

import javax.validation.Valid

//@Aspect
//@Component
class MessageQueueService {


//    @Value('${rabbitmq.exchanges.name}')
    static String EXCHANGE_NAME = "exchange01"
//    static final String QUEUE_NAME_01 = "queue01"
//    static final String QUEUE_NAME_02 = "queue02"
//    static final String ROUTING_KEY_01 = "key01"
//    static final String ROUTING_KEY_02 = "key02"


    RabbitMessagePublisher rabbitMessagePublisher
    RabbitContext rabbitContext
    QueueBuilder queueBuilder

    def serviceMethod() {
    }

    def createdQueueAndSendMessage(Long eventId, String message){
        String eventQueue = "Event${eventId}"
        String eventQueueKey = "event.${eventId}"

        rabbitMessagePublisher.withChannel {channel ->
            channel.queueDeclare(eventQueue, true, false, false, null)
            channel.queueBind(eventQueue, EXCHANGE_NAME, eventQueueKey)

            rabbitMessagePublisher.send(EXCHANGE_NAME, eventQueueKey, message)
        }
//        rabbitMessagePublisher.send(EXCHANGE_NAME, eventQueueKey, message)
    }

    def getMessage(Long eventId){

    }
}
