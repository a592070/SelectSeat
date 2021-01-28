package aop

import grails.gorm.transactions.Transactional
import org.apache.log4j.Logger
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.aspectj.lang.annotation.Pointcut

import org.springframework.stereotype.Component


@Aspect
@Component
class LogAopService {

    static Logger logger = Logger.getLogger(LogAopService.class)

    def serviceMethod() {
    }
    @Pointcut("execution(* selectseat.DemoRedisController.*(..))")
    def pointCut(){}

    @Before("pointCut()")
    def doBefore(){
        logger.debug("================aop before")
        println "================aop before"
    }
}
