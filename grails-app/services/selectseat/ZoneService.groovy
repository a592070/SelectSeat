package selectseat

import grails.gorm.services.Query
import grails.gorm.services.Service
import grails.gorm.transactions.Transactional
import groovy.transform.CompileStatic
import org.springframework.stereotype.Component

@CompileStatic
interface IZoneService {
//    @Query("select count(1) from ${Seat} s, ${SeatMap} m where s.seatMap.id = m.id and s.status = 0 and m.zone.id = ${zoneId} group by m.zone.id")
//    Number countSeat(Long zoneId)
}
@Component
@Transactional
@Service(Zone)
abstract class ZoneService implements IZoneService{

}
