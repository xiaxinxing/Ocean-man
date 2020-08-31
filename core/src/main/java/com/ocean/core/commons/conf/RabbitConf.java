package com.ocean.core.commons.conf;


import org.springframework.context.annotation.Configuration;

/**
 * @author : xxx
 * @date : 2020-03-11 17:49
 **/
@Configuration
public class RabbitConf {

    /**
     * 队列 起名：TestDirectQueue //true 是否持久
     */
  /*  @Bean
    public Queue testDirectQueue() {
        return new Queue("TestDirectQueue", true);
    }
*/
    /**
     * Direct交换机 起名：TestDirectExchange
     */
  /*  @Bean
    DirectExchange testDirectExchange() {
        return new DirectExchange("TestDirectExchange");
    }
*/
    //绑定  将队列和交换机绑定, 并设置用于匹配键：TestDirectRouting
    /*@Bean
    Binding bindingDirect() {
        return BindingBuilder.bind(testDirectQueue()).to(testDirectExchange()).with("TestDirectRouting");
    }*/

}
