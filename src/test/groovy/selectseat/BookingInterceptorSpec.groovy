package selectseat


import grails.testing.web.interceptor.InterceptorUnitTest
import spock.lang.Specification

class BookingInterceptorSpec extends Specification implements InterceptorUnitTest<BookingInterceptor> {

    def setup() {
    }

    def cleanup() {

    }

    void "Test booking interceptor matching"() {
        when:"A request matches the interceptor"
            withRequest(controller:"booking")

        then:"The interceptor does match"
            interceptor.doesMatch()
    }
}
