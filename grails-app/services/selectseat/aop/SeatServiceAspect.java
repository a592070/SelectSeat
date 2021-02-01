package selectseat.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class SeatServiceAspect {
    @Pointcut("execution(* selectseat.SeatService.getColumnSeat(String)) && args(seatCode)")
    public void getColumnSeatPoint(String seatCode){}

    @Before(value = "getColumnSeatPoint(seatCode)", argNames = "seatCode")
    public void beforeMethod(String seatCode){
        System.out.println("-- Before Method" + seatCode);
    }
}
