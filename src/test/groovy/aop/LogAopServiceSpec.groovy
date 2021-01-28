package aop

import grails.testing.services.ServiceUnitTest
import spock.lang.Specification

class LogAopServiceSpec extends Specification implements ServiceUnitTest<LogAopService>{

    def setup() {
    }

    def cleanup() {
    }

    void "test something"() {
        expect:"fix me"
            true == false
    }
}
