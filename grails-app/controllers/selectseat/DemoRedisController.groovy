package selectseat


import grails.plugins.redis.RedisService


class DemoRedisController {

    static RedisService redisService

    def index() {
//        def bitmap = [0,1,0,1,0,0,0,0,0,1,1,1,0,1,0] // 15
        def bitmap2 = ["a":[1,0,0,0,0,0,0,0,1],
                       "b":[0,1,0,0,0,0,0,1,0],
                       "c":[0,0,1,0,0,0,1,0,0],
                       "d":[0,0,0,1,0,1,0,0,0],
                       "e":[0,0,0,0,1,0,0,0,0]]

//        redisService.withRedis { Jedis redis ->
//            for (i in 0..<bitmap2.size()) {
//                redis.setbit("bitmap", i, bitmap.get(i)==1)
//            }
//        }

//        String key = "event:1:zone:1:seats"
//        def zoneSeat = new ZoneSeat()
//        def seatMap = []
//        for (i in 0..<10) {
//            seatMap.add(i)
//        }
//        zoneSeat.seatMap = seatMap
//        zoneSeat.save()

//        def person = new Person()
//        person.firstName = "gaga"
//        person.lastName = "xie"
//        person.redis.save()


//        redisService.withRedis { RedisClient redis ->
//            def commands = redis.connect().async()
//            commands.set("bitmap", "bitmap")
//        }


//        bitmap2.each {
//            def temp = it
//            println(it.key)
//            println(it.value)
//            redisService.memoizeHash(temp.key, {temp.value.toString()})
//        }

//        redisService.withRedis { Jedis redis ->
//            def tmp = (Math.random() + 0.5)
//            println tmp
//            redis.setbit("bitmap", 14, tmp.toInteger() == 1)
//        }

//        def tmp = []
//        redisService.withRedis { Jedis redis ->
//            for (i in 0..<bitmap.size()) {
//                 tmp.add(redis.getbit("bitmap", i))
//            }
//            println(tmp)
//        }

//        redisService.withRedis { RedisClient redis ->
//            def commands = redis.connect().async()
//            commands.set("bitmap", "bitmap")
//        }


//        def tmp = ZoneSeat.get(1)
//        def tmp = new ZoneSeat()
//        tmp.eventId = 1
//        tmp.zoneId = 1
////        redisService.set("person", tmp as JSON)
//        redisService.memoizeObject(ZoneSeat, "person", tmp)

//        println(tmp)
        def keys = redisService.keys("*")
        println(keys)
        def person = redisService.person
        println person
        render "null"
    }
}
