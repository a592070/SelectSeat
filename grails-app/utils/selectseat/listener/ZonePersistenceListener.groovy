package selectseat.listener

import org.grails.datastore.mapping.core.Datastore
import org.grails.datastore.mapping.engine.event.AbstractPersistenceEvent
import org.grails.datastore.mapping.engine.event.AbstractPersistenceEventListener
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationEvent
import org.springframework.stereotype.Component
import selectseat.redis.SelectSeatRedisService

@Component
class ZonePersistenceListener {

//    ZonePersistenceListener(Datastore datastore) {
//        super(datastore)
//    }
//
//    @Override
//    protected void onPersistenceEvent(AbstractPersistenceEvent event) {
//        switch (event.eventType){
//
//        }
//    }
//
//    @Override
//    boolean supportsEventType(Class<? extends ApplicationEvent> eventType) {
//        return super.supportsSourceType(eventType)
//    }
}
