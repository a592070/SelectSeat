import org.springframework.aop.aspectj.annotation.AnnotationAwareAspectJAutoProxyCreator

// Place your Spring DSL code here
beans = {
    xmlns context: 'http://www.springframework.org/schema/context'
    context.'component-scan'( 'base-package' : 'selectseat' )
//    autoProxyCreator(AnnotationAwareAspectJAutoProxyCreator) {
//        proxyTargetClass = true
//    }
    xmlns aop:"http://www.springframework.org/schema/aop"
//    loggerAspect(selectseat.aop.LoggerAspect)
    aop{
        config("proxy-target-class":true) {
        }
    }

}
