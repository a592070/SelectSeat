package selectseat.messageQueue

import com.budjb.rabbitmq.RabbitContext
import com.budjb.rabbitmq.publisher.RabbitMessagePublisher
import com.budjb.rabbitmq.queuebuilder.QueueBuilder
import com.budjb.rabbitmq.queuebuilder.QueueBuilderImpl
import com.rabbitmq.client.Channel
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class RoutingMQ {
    static final String EXCHANGE_NAME = "channel01"

    @Autowired
    RabbitMessagePublisher rabbitMessagePublisher
    @Autowired
    RabbitContext rabbitContext

    def method1(){
        def channel = rabbitContext.createChannel()
        channel.exchangeDeclare(EXCHANGE_NAME, "direct")

        rabbitMessagePublisher.send(EXCHANGE_NAME, "key1", "channel01 key1 message")
    }
}
