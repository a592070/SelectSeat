package selectseat.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class SeatServiceAspect {
    @Pointcut("execution(* selectseat.DemoRedisController.*(..))")
    public void point(){}

    @Before("point()")
    public void beforeMethod(){
        System.out.println("--- Before Method DemoRedisController ---");
    }

//    @Pointcut("execution(* selectseat.SeatService.getColumnSeat(..)) && args(seatCode)")
//    public void getColumnSeatPoint(String seatCode){}
//
//    @Before("getColumnSeatPoint(seatCode)")
//    public void beforeMethod(String seatCode){
//        System.out.println("-- Before Method" + seatCode);
//    }

    @Pointcut("execution(* selectseat.SeatService.getColumnSeat(String)) && args(seatCode)")
    public void getColumnSeatPoint(String seatCode){}

    @Before("getColumnSeatPoint(seatCode)")
//    @Before("execution(* selectseat.SeatService.getColumnSeat(..))")
    public void beforeMethod2(JoinPoint joinPoint, String seatCode){
        System.out.println("-- Before Method getColumnSeatPoint --"+ seatCode);
        System.out.println(Arrays.toString(joinPoint.getArgs()));
    }

//    @Before("@annotation(selectseat.annotation.SeatServiceAspect)")
//    public void beforeMethod3(JoinPoint joinPoint){
//        System.out.println("-- Before Method getColumnSeatPoint --"+ joinPoint.getSignature().getName());
//    }
//    @Before("getColumnSeatPoint()")
//    public void beforeMethod2(JoinPoint joinPoint){
//        System.out.println("-- Before Method getColumnSeatPoint --");
//    }
}
