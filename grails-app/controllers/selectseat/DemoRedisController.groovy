package selectseat

import grails.converters.JSON
import grails.plugins.redis.RedisService


class DemoRedisController {

    static RedisService redisService
    def selectedService
    def demoService
    def seatService
    def routingMQ

    def index() {
//        def bitmap = [0,1,0,1,0,0,0,0,0,1,1,1,0,1,0] // 15
        def bitmap2 = ["a":[1,0,0,0,0,0,0,0,1],
                       "b":[0,1,0,0,0,0,0,1,0],
                       "c":[0,0,1,0,0,0,1,0,0],
                       "d":[0,0,0,1,0,1,0,0,0],
                       "e":[0,0,0,0,1,0,0,0,0]]

        println 'In DemoController.index()'
//        demoService.serviceMethod()
        seatService.getColumnSeat(Zone.get(1).zoneCode, "A")

        routingMQ.method1()


        render 'Hello World'
    }
}
