package selectseat

import grails.gorm.services.Query
import grails.gorm.services.Service
import grails.gorm.transactions.Transactional
import groovy.transform.CompileStatic
import org.springframework.stereotype.Component

@CompileStatic
interface IOrderListService{

    @Query("select count(1) from ${OrderList} ol, ${OrderDetail} od where ol.id = od.order.id and ol.user.id = ${query} and ol.event.id = ${eventId} group by ol.id")
    Number countTicket(Long query,Long eventId)
}
@Component
@Transactional
@Service(OrderDetail)
abstract class OrderListService implements IOrderListService{

}
