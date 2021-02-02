package selectseat

import grails.gorm.transactions.Transactional
import org.springframework.amqp.core.AmqpAdmin
import org.springframework.amqp.core.Binding
import org.springframework.amqp.core.ExchangeTypes
import org.springframework.amqp.core.Message
import org.springframework.amqp.core.MessageListener
import org.springframework.amqp.core.MessageProperties
import org.springframework.amqp.core.TopicExchange
import org.springframework.amqp.rabbit.annotation.Exchange
import org.springframework.amqp.rabbit.annotation.Queue
import org.springframework.amqp.rabbit.annotation.QueueBinding
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.amqp.rabbit.core.RabbitAdmin
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class DynamicRabbitMQService{

    RabbitTemplate rabbitTemplate
    @Autowired
//    RabbitAdmin rabbitAdmin
    AmqpAdmin rabbitAdmin
    public static final String EXCHANGE = "exchange01";


    def serviceMethod() {
    }
    def method1(Long eventId, String message){
        String eventQueue = "Event${eventId}"
        String eventQueueKey = "event.${eventId}"
        def admin = new RabbitAdmin(rabbitTemplate.getConnectionFactory())
        admin.declareExchange(new TopicExchange(EXCHANGE))
        admin.declareQueue(new org.springframework.amqp.core.Queue(eventQueue))
        admin.declareBinding()

        rabbitTemplate.execute()


        rabbitTemplate.setExchange(EXCHANGE)
        rabbitTemplate.setQueue(eventQueue)
//        rabbitAdmin.declareExchange(new TopicExchange(EXCHANGE))
        rabbitTemplate.convertAndSend("你好我不好")

    }

    @RabbitListener(bindings=@QueueBinding(
            value = @Queue("Event1"),
            exchange = @Exchange(value = "exchange01", type = ExchangeTypes.TOPIC),
            key="event.1"))
    def method(String content){
        println this.getClass().getName() + "=============" + content
    }

}
