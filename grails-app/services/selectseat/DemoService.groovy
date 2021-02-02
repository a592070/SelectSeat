package selectseat

import grails.gorm.transactions.Transactional

@Transactional
class DemoService {

    def serviceMethod() {
        println 'In DemoService.serviceMethod()'
    }
}