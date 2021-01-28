package selectseat

import grails.testing.web.interceptor.InterceptorUnitTest
import spock.lang.Specification

class TicketDetailInterceptorSpec extends Specification implements InterceptorUnitTest<TicketDetailInterceptor> {

    def setup() {
    }

    def cleanup() {

    }

    void "Test ticketDetail interceptor matching"() {
        when:"A request matches the interceptor"
            withRequest(controller:"ticketDetail")

        then:"The interceptor does match"
            interceptor.doesMatch()
    }
}
