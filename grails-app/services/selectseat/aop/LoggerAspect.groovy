package selectseat.aop

import org.aspectj.lang.annotation.After
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.springframework.stereotype.Component

@Aspect
@Component
class LoggerAspect {
    //A more generic advice would be as below
    @Before("execution(* selectseat.DemoService.serviceMethod(..))")
//    @Before("selectseat.DemoService.serviceMethod(..)")
    def beforeMethod(){
        println '-- Before Method.'
    }

    //A more generic advice would be as below
    //@Around("execution(* com.test.*.*(..))")
//    @After("selectseat.DemoService.serviceMethod()")
//    def afterMethod(){
//        println '-- After Method.'
//    }
}
