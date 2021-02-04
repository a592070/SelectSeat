package selectseat.listener

import com.budjb.rabbitmq.queuebuilder.ExchangeType
import org.springframework.amqp.core.ExchangeTypes
import org.springframework.amqp.rabbit.annotation.Argument
import org.springframework.amqp.rabbit.annotation.Exchange
import org.springframework.amqp.rabbit.annotation.Queue
import org.springframework.amqp.rabbit.annotation.QueueBinding
import org.springframework.amqp.rabbit.annotation.RabbitHandler
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component
import selectseat.utils.StringUtils

import static selectseat.utils.StringUtils.*

@Component
//@RabbitListener(bindings = @QueueBinding(
//        key = DELAY_MESSAGE_KEY,
//        value = @Queue(DELAY_MESSAGE_QUEUE),
//        exchange = @Exchange(
//                value = DELAY_MESSAGE_EXCHANGE,
//                type = ExchangeTypes.DELAYED,
//                delayed = "true",
//                arguments = @Argument(
//                        name = "x-delayed-type",
//                        type = ExchangeTypes.DIRECT
//                )
//        )
//))
@RabbitListener(queues = "DELAY_MESSAGE_QUEUE_01")
class DelayMessageListener {
    @RabbitHandler
    def onMessage(String msg){
        println new Date().format("yyyy/MM/dd HH:mm:ss")+"========"+msg


    }
}
