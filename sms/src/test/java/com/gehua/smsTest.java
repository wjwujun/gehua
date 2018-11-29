package com.gehua;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class smsTest {

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Test
    public  void  testSend() throws InterruptedException {
        Map<String, String> msg = new HashMap<>();
        msg.put("phone","18200142543");
        msg.put("code","123456");
        amqpTemplate.convertAndSend("sms.exchange","sms.verfity",msg);

    }
}
