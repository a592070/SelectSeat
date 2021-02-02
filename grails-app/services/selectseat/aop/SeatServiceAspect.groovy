package selectseat.aop

import grails.plugins.redis.RedisService
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.After
import org.aspectj.lang.annotation.AfterReturning
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.aspectj.lang.annotation.Pointcut
import org.springframework.stereotype.Component
import redis.clients.jedis.Jedis
import selectseat.SelectSeatRedisService

@Aspect
@Component
class SeatServiceAspect {
    SelectSeatRedisService selectSeatRedisService;

    @Pointcut("execution(* selectseat.DemoRedisController.*(..))")
    public void point(){}

    @Before("point()")
    public void beforeMethod(){
        System.out.println("--- Before Method DemoRedisController ---");
    }
    @Pointcut("execution(* selectseat.SeatService.getColumnSeat(String)) && args(seatCode)")
    public void getColumnSeatPoint(String seatCode){}

    @Before("getColumnSeatPoint(seatCode)")
//    @Before("execution(* selectseat.SeatService.getColumnSeat(..))")
    public void beforeMethod2(JoinPoint joinPoint, String seatCode){
        System.out.println("-- Before Method getColumnSeatPoint --"+ seatCode);
        System.out.println(Arrays.toString(joinPoint.getArgs()));
    }
    @Pointcut()
    public void queryEmptySeat(Long eventId){}
    @Before("queryEmptySeat(eventId)")
    def beforeQueryEmptySeat(Long eventId){
        if(selectSeatRedisService.existEmptySeat(eventId)) return selectSeatRedisService.getEmptySeat(eventId) as int
    }
    @AfterReturning("queryEmptySeat(eventId)")
    def afterQueryEmptySeat(Long eventId, int returnValue){
        selectSeatRedisService.setEmptySeat(eventId, returnValue)
    }

}
