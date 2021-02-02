package spring

import org.springframework.aop.aspectj.annotation.AnnotationAwareAspectJAutoProxyCreator

// Place your Spring DSL code here
beans = {
    xmlns context: 'http://www.springframework.org/schema/context'
//    context.'component-scan'( 'base-package' : 'selectseat' )
//    autoProxyCreator(AnnotationAwareAspectJAutoProxyCreator) {
//        proxyTargetClass = true
//    }

//    seatServiceAspect(selectseat.aop.SeatServiceAspect)
//    xmlns aop:"http://www.springframework.org/schema/aop"
//    aop{
//        config("proxy-target-class":true) {
//            aspect(id: "seatServiceAspect1", ref: "seatServiceAspect"){
//                before method: "beforeMethod2",
//                pointcut: "execution(* selectseat.SeatService.getColumnSeat(..)) && args(seatCode)"
//            }
//        }
//    }

}
