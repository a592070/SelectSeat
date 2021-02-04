package selectseat

import grails.testing.web.taglib.TagLibUnitTest
import spock.lang.Specification

class SimpleTagLibSpec extends Specification implements TagLibUnitTest<SimpleTagLib> {

    def setup() {
    }

    def cleanup() {
    }

    void "test something"() {
        expect:"fix me"
            true == false
    }
}
