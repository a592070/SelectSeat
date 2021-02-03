package selectseat.aop

import grails.plugins.redis.RedisService
import groovy.util.logging.Slf4j
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.After
import org.aspectj.lang.annotation.AfterReturning
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.aspectj.lang.annotation.Pointcut
import org.springframework.stereotype.Component
import redis.clients.jedis.Jedis
import selectseat.Event
import selectseat.SelectSeatRedisService
import selectseat.Zone

@Slf4j
@Aspect
@Component
class SeatServiceAspect {
    SelectSeatRedisService selectSeatRedisService;

    @Pointcut("execution(* selectseat.DemoRedisController.*(..))")
    public void point(){}

    @Before("point()")
    public void beforeMethod1(){
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



    @Pointcut("@annotation(selectseat.annotation.QueryEmptySeatAspect)")
    public void annotationQueryEmptySeat(){}

    @Pointcut("execution(* *.*(..)) && args(param1)")
    public void pointCutParam(Object param1){}

    @Around("annotationQueryEmptySeat() && args(param1)")
    def beforeQueryEmptySeat(ProceedingJoinPoint joinPoint, String param1){
        println "======="+this.class.getName()+"=====joinPoint"+joinPoint.signature


//        if(selectSeatRedisService.existEmptySeat(param1)){
//            return selectSeatRedisService.getEmptySeat(param1) as int
//        }
//        if(param1 == "1"){
//            println "query zone by eventId="+param1
            joinPoint.proceed(param1)
//        }else{
//            def zoneList = Zone.findAllByEvent(Event.get(Long.valueOf(param1)+1))
//            return [zoneList: zoneList]
//        }
    }

}
