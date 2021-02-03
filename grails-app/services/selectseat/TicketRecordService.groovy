package selectseat

import grails.gorm.services.Query
import grails.gorm.services.Service
import grails.gorm.transactions.Transactional
import groovy.transform.CompileStatic

@CompileStatic
interface ITicketRecordService{
    void delete (Serializable id)
    TicketRecord save(Long id, String type, int count)
    TicketRecord findByRecordId(Long id)


    @Query("select t.count from ${TicketRecord} t where t.id = $recordIdParam")
//    @Query("select t.count from TicketRecord t where t.id = recordIdParam")
    List<Integer> findTicketRecordId(Long recordIdParam)

    @Query("update ${TicketRecord} t set t.count = t.count + 1 where t.id = $recordIdParam")
//    @Query("update TicketRecord t set t.count = t.count + 1 where t.id = recordIdParam")
    Number updateViews(Long recordIdParam)
}

@Service(TicketRecord)
abstract  class TicketRecordService implements ITicketRecordService{

    @Transactional
    void increment(Long id, String type) {
        List<Integer> views = findTicketRecordId(id)
        if (!views) {
            save(id, type, 1)
        } else {
            updateViews(id)
        }
    }
}
