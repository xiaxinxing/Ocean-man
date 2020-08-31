package com.ocean.core.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RequestMapping("test")
@RestController
public class TestController {

   /* @Autowired
    JmsMessagingTemplate jmsMessagingTemplate;

    @Autowired
    AmqpTemplate amqpTemplate;

    private String rabbitMqTest = "TestDirectQueue";

    private String activeMqTest = "active_mq_test";

    @RequestMapping("/jms")
    public String sendJmsMessage(String message) {
        jmsMessagingTemplate.convertAndSend(activeMqTest, message);
        return "发送成功: " + message;
    }


    @RequestMapping("/rabbit")
    public String sendRabbitMessage(String message) {
        amqpTemplate.convertAndSend(rabbitMqTest, message);
        return "发送成功:" + message;
    }*/

    public static void main(String[] args) {
        BigDecimal one = new BigDecimal("-1");
        for (int i = 0; i < 50; i++) {
            System.out.print(new BigDecimal(2 * i+1).multiply(one.pow(i + 1)).toString()+",");
        }

    }
}
