//import org.springframework.amqp.core.BindingBuilder
//import org.springframework.amqp.core.Queue
//import org.springframework.amqp.core.TopicExchange
//import org.springframework.amqp.rabbit.connection.ConnectionFactory
//import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer
//import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter

// Place your Spring DSL code here
beans = {
    xmlns aop:"http://www.springframework.org/schema/aop"
    aop.config("proxy-target-class":true) {}

}
