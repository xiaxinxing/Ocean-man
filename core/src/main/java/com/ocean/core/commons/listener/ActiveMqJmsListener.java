package com.ocean.core.commons.listener;

import org.springframework.stereotype.Component;



@Component
public class ActiveMqJmsListener {

    /*@JmsListener(destination="active_mq_test")
    public void OnMessage(Message message) throws JMSException {
        TextMessage textMessage= (TextMessage) message;
        System.out.println("MyJmsListener的消费任务:"+textMessage.getText());

    }*/

}
