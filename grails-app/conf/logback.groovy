import grails.util.BuildSettings
import grails.util.Environment
import org.springframework.boot.logging.logback.ColorConverter
import org.springframework.boot.logging.logback.WhitespaceThrowableProxyConverter

import java.nio.charset.Charset

conversionRule 'clr', ColorConverter
conversionRule 'wex', WhitespaceThrowableProxyConverter

// See http://logback.qos.ch/manual/groovy.html for details on configuration
def logPattern = '[%-5p][%thread] %d{MM-dd HH:mm:ss.SSS} %c{0} - %m%n'
appender('STDOUT', ConsoleAppender) {
    encoder(PatternLayoutEncoder) {
        charset = Charset.forName('UTF-8')

        pattern = logPattern
//                '%clr(%d{yyyy-MM-dd HH:mm:ss.SSS}){faint} ' + // Date
//                        '%clr(%5p) ' + // Log level
//                        '%clr(---){faint} %clr([%15.15t]){faint} ' + // Thread
//                        '%clr(%-45.45logger{39}){cyan} %clr(:){faint} ' + // Logger
//                        '%m%n%wex' // Message
    }
}

def targetDir = BuildSettings.TARGET_DIR
if (Environment.isDevelopmentMode() && targetDir != null) {
    appender("FULL_STACKTRACE", FileAppender) {
        file = "${targetDir}/stacktrace.log"
        append = true
        encoder(PatternLayoutEncoder) {
            pattern = "%level %logger - %msg%n"
        }
    }
    logger("StackTrace", ERROR, ['FULL_STACKTRACE'], false)
}
root(ERROR, ['STDOUT'])
logger("org.hibernate.SQL", DEBUG, ["STDOUT"], false)
logger ('org.hibernate.type.descriptor.sql.BasicBinder', TRACE, ['STDOUT'], false)
logger ('org.hibernate.engine.transaction.internal.TransactionImpl', DEBUG, ['STDOUT'], false)
logger ('grails.plugins.redis.RedisService', DEBUG, ['STDOUT'], false)

//debug 'grails.app.service'
//debug 'org.codehaus.groovy.grails.plugins'