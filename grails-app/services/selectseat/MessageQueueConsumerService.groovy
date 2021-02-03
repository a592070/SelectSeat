package selectseat

import com.budjb.rabbitmq.RabbitContext
import com.budjb.rabbitmq.consumer.RabbitMessageHandler
import com.rabbitmq.client.AMQP
import com.rabbitmq.client.Channel
import com.rabbitmq.client.DefaultConsumer
import com.rabbitmq.client.Envelope
import grails.gorm.transactions.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import javax.annotation.PostConstruct

@Component
@Transactional
class MessageQueueConsumerService {

//    @Autowired
//    def rabbitMessageHandler
    RabbitContext rabbitContext
    static String EXCHANGE_NAME = "exchange01"

    def serviceMethod() {
    }


    @PostConstruct
    def createQueue(){
        Channel channel = rabbitContext.createChannel()
        channel.exchangeDeclare(EXCHANGE_NAME, "topic", true)
        def queue = channel.queueDeclare().getQueue()
        channel.queueBind(queue, EXCHANGE_NAME, "")

        def consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                               AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                println message
                if(Objects.equals(message, null)){
                    return
                }
            }
        }
        channel.basicConsume(queue, true, consumer)
    }
}
