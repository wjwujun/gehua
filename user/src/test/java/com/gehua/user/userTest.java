package com.gehua.user;


import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
public class userTest {

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    StringRedisTemplate stringRedisTemplate;

    /*redis测试*/
    @Test
    public void sendCode(){
        //生成六位数字随机数
        String checkcode = RandomStringUtils.randomNumeric(6);

        /*
         * 向redis中放一份
         * 设置过期时间
         * redisTemplate默认的序列化方式是二进制改为string
         *
         * */
        /*redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<Object>(Object.class));
        redisTemplate.setHashValueSerializer(new Jackson2JsonRedisSerializer<Object>(Object.class));*/
        redisTemplate.opsForValue().set("checkcode_"+"18200142543", checkcode, 1, TimeUnit.MINUTES);//默认使用 JDK 对 key 和 value 进行序列化，转成字节存入 Redis。



        //给用户发一份
        /*Map<String, String> map = new HashMap<>();
        map.put("mobile", mobile);
        map.put("checkcode", checkcode);*/
        //rabbitTemplate.convertAndSend("sms", map);

        Object bbb = redisTemplate.opsForValue().get("checkcode_18200142543");


        //在控制台显示一份【方便测试】
        System.out.println("验证码为bbb："+bbb);
    }

}
