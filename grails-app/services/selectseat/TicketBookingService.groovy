package selectseat

import grails.gorm.services.Service
import grails.gorm.transactions.ReadOnly
import groovy.transform.CompileDynamic
import groovy.transform.CompileStatic
import org.grails.datastore.mapping.query.api.BuildableCriteria
import org.hibernate.transform.Transformers

interface ITicketDataService{
    Ticket save(String type, int price)
    Number count
    Ticket findById(Long id)
}

@Service(Ticket)
abstract class TicketBookingService implements ITicketDataService{

    @CompileDynamic
    @ReadOnly
    List<Ticket> findAll() {
        BuildableCriteria c = Ticket.createCriteria()
        c.list {
            resultTransformer(Transformers.aliasToBean(Ticket))
            projections {
                property('id', 'id')
                property('price', 'price')
                property('type', 'type')
            }
        }
    }
}
