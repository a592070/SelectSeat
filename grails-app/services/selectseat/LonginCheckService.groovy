package selectseat

import grails.gorm.services.Query
import grails.gorm.services.Service
import grails.gorm.transactions.Transactional
import groovy.transform.CompileStatic
import org.springframework.stereotype.Component

@CompileStatic
interface ILonginCheckService{
    @Query("select id from ${User} where email = ${userMail}")
    Long searchUser(String userMail)

}

@Transactional
@Service(User)
@Component
abstract class LonginCheckService implements ILonginCheckService {


//    ILonginCheckService iLonginCheckService
//
//    def countUserTicketByEvent(String query, Long eventId){
//        def userId = iLonginCheckService.searchUser(query)
//        def ticketNum = countTicket(userId, eventId)
//        return [ticketNum: ticketNum]
//    }
}
