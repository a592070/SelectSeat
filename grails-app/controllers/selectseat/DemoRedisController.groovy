package selectseat

import grails.async.Promise
import grails.async.PromiseList
import grails.plugins.redis.RedisService
import org.springframework.amqp.AmqpException
import org.springframework.amqp.core.Message
import org.springframework.amqp.core.MessageBuilder
import org.springframework.amqp.core.MessagePostProcessor
import org.springframework.amqp.core.MessageProperties
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Component
import selectseat.redis.SelectSeatRedisService

import static selectseat.utils.StringUtils.*


@Component
class DemoRedisController {

//    static RedisService redisService
//    def selectedService
//    def seatService
//    @Autowired
//    RoutingMQ routingMQ

//    def messageQueueService
//    def dynamicRabbitMQService

    SelectSeatRedisService selectSeatRedisService
    RabbitTemplate rabbitTemplate

    def bookingService

    def index() {
//        def bitmap = [0,1,0,1,0,0,0,0,0,1,1,1,0,1,0] // 15
//        def bitmap2 = ["a":[1,0,0,0,0,0,0,0,1],
//                       "b":[0,1,0,0,0,0,0,1,0],
//                       "c":[0,0,1,0,0,0,1,0,0],
//                       "d":[0,0,0,1,0,1,0,0,0],
//                       "e":[0,0,0,0,1,0,0,0,0]]

        println 'In DemoController.index()'
//        demoService.serviceMethod()

//        String seatCode = Zone.get(1).zoneCode + "SA"
//        seatService.getColumnSeat(seatCode)

//        messageQueueService.createdQueueAndSendMessage(1, "hello 你好嗎")
//        dynamicRabbitMQService.method1(1, "你好我不好")

//        rabbitTemplate.convertAndSend("exchange01", "event.1", "你好我不好")

//        for (i in 0..<10) {
//            int num = i
//            new Thread(new Runnable() {
//                @Override
//                void run() {
//                    dynamicRabbitMQService.method1(1, "你好我不好"+Thread.currentThread().getName())
//                }
//            }).start()
//        }
//        render bookingService.searchEventZone("2")

//        def order = OrderList.get(1)
//        println "=========="
//        def order1 = OrderDetail.countByOrder(order)
//        render order1


//        rabbitTemplate.convertAndSend(
//                DELAY_MESSAGE_EXCHANGE,
//                DELAY_MESSAGE_KEY,
//                "this is a message ",
//                new MessagePostProcessor() {
//                    @Override
//                    Message postProcessMessage(Message message) throws AmqpException {
//                        message.messageProperties.delay = 5*1000
//                        return message
//                    }
//                })

//        def zone = new Zone(id: 99, name: "活動999的 A 區", columnCount:5, rowCount: 5, totalSeat: 20)
//        def disabledSeat = [[0,1],[0,2],[1,0],[2,1],[3,1]]
        // 0 x x 0 0
        // x 0 0 0 0
        // 0 x 0 0 0
        // 0 x 0 0 0
        // 0 0 0 0 0

//        selectSeatRedisService.initZoneSeatValue(zone, disabledSeat)

//        println selectSeatRedisService.countZoneEmptySeat(99)
//        selectSeatRedisService.incrZoneSeat(99, 1, 2)


        def map = [
                "2-0":1, "2-1":0, "2-2":0, "2-3":0,
                "0-0":1, "0-1":0, "0-3":0, "0-2":0,
                "1-3":1, "1-2":0, "1-1":0, "1-0":0,
        ]
//        def map = [
//                "0-0":1, "0-1":0, "0-2":0, "0-3":0,
//                "1-0":1, "1-1":0, "1-2":0, "1-3":0,
//                "2-0":1, "2-1":0, "2-2":0, "2-3":0,
//        ]


//        def seats = Zone.get(11).getSeats()
//        println seats

//        String tmp = "ROW:01_COL:01"
//        println tmp.lastIndexOf("ROW:")
//        println tmp.lastIndexOf("_COL:")
//        println tmp.split("_")

        println Ticket.get(1).getTypeName()
        render new Date().format("yyyy/MM/dd HH:mm:ss")+"=======send Message"

//        render 'Hello World'
    }
}
