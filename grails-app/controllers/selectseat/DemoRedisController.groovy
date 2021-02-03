package selectseat

import grails.async.Promise
import grails.async.PromiseList
import grails.plugins.redis.RedisService
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Component

@Component
class DemoRedisController {

    static RedisService redisService
    def selectedService
    def demoService
    def seatService
//    @Autowired
//    RoutingMQ routingMQ

//    def messageQueueService
//    def dynamicRabbitMQService
//    RabbitTemplate rabbitTemplate

    def bookingService

    def index() {
//        def bitmap = [0,1,0,1,0,0,0,0,0,1,1,1,0,1,0] // 15
        def bitmap2 = ["a":[1,0,0,0,0,0,0,0,1],
                       "b":[0,1,0,0,0,0,0,1,0],
                       "c":[0,0,1,0,0,0,1,0,0],
                       "d":[0,0,0,1,0,1,0,0,0],
                       "e":[0,0,0,0,1,0,0,0,0]]

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
        render bookingService.searchEventZone("2")

//        render 'Hello World'
    }
}
