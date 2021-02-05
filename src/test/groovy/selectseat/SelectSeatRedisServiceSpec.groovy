package selectseat

import grails.plugins.redis.RedisService
import grails.testing.mixin.integration.Integration
import grails.testing.services.ServiceUnitTest
import org.springframework.beans.factory.annotation.Autowired
import redis.clients.jedis.Jedis
import redis.clients.jedis.JedisMonitor
import selectseat.redis.SelectSeatRedisService
import spock.lang.Specification
@Integration
class SelectSeatRedisServiceSpec extends Specification implements ServiceUnitTest<SelectSeatRedisService>{

    def setup() {
    }

    def cleanup() {
    }
    @Autowired
    RedisService redisService
    void "test something"() {
        expect:
        new Thread(new Runnable() {
            public void run() {
                try {
                    // sleep 100ms to make sure that monitor thread runs first
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace()
                }
                redisService.withRedis { Jedis jedis ->
                    for (int i = 0; i < 5; i++) {
                        jedis.incr("foobared");
                    }
                }

            }
        }).start();

        redisService.withRedis { Jedis jedis ->
            jedis.monitor(new JedisMonitor() {
                private int count = 0;

                public void onCommand(String command) {
                    println command
                    if (command.contains("INCR")) {
                        count++;
                    }
                    println count
//                    if (count == 5) {
//                        client.disconnect();
//                    }
                }
            });
        }
        true == false
    }
}
