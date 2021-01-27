package selectseat

import grails.plugins.redis.RedisService
import redis.clients.jedis.Jedis

class DemoRedisController {

    RedisService redisService

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
        redisService.hmset("bitmap", bitmap2)
//        redisService.memoizeHash("bitmap", bitmap2, null)

//        redisService.withRedis { Jedis redis ->
//            def tmp = (Math.random() + 0.5)
//            println tmp
//            redis.setbit("bitmap", 14, tmp.toInteger() == 1)
//        }

        def tmp = []
//        redisService.withRedis { Jedis redis ->
//            for (i in 0..<bitmap.size()) {
//                 tmp.add(redis.getbit("bitmap", i))
//            }
//            println(tmp)
//        }
        tmp = redisService.hget("bitmap")
        println(tmp)
        render tmp
    }
}
