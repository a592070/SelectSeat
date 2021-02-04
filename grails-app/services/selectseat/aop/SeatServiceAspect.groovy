package selectseat.aop


import groovy.util.logging.Slf4j
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.aspectj.lang.annotation.Pointcut
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import selectseat.redis.SelectSeatRedisService

@Slf4j
@Aspect
@Component
class SeatServiceAspect {
    @Autowired
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

    @Pointcut("execution(* *+.*(..)) && args(param1)")
    public void pointCutParam(Object param1){}

    @Around("annotationQueryEmptySeat() && args(zoneId)")
//    @Around("annotationQueryEmptySeat() && pointCutParam(param1)")
//    @Around("execution(* selectseat.booking.IBookingService+.countEmptySeat(..)) && args(zoneId)")
    def beforeQueryEmptySeat(ProceedingJoinPoint joinPoint, Long zoneId){
//        def zoneId = param1 as Long
        println "======="+this.class.getName()+"=====joinPoint"+joinPoint.signature


        def emptySeat = selectSeatRedisService.getEmptySeat(zoneId)
        if(emptySeat == null){
            // lock on
            if(selectSeatRedisService.setEmptySeatMutexLock(zoneId)){
                try {
                    selectSeatRedisService.expireEmptySeatMutexLock(zoneId)

                    // query from DB
                    emptySeat = joinPoint.proceed(zoneId) as int

                    selectSeatRedisService.setEmptySeat(zoneId, emptySeat, 60)
                }catch(Exception e){
                    e.printStackTrace()
                }finally{
                    selectSeatRedisService.delEmptySeatMutexLock(zoneId)
                }
            }
        }
        selectSeatRedisService.setExpireEmptySeat(zoneId)
        return emptySeat




//        if(param1 == "1"){
//            println "query zone by eventId="+param1
//            joinPoint.proceed(zoneId)
//        }else{
//            def zoneList = Zone.findAllByEvent(Event.get(Long.valueOf(param1)+1))
//            return [zoneList: zoneList]
//        }
    }

}
