package com.gehua.service;

import com.gehua.common.utils.Result;
import com.gehua.common.utils.StatusCode;
import com.gehua.mapper.UserMapper;
import com.gehua.userPojo.User;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.TimeUnit;


@Service
public class UserService {
    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private BCryptPasswordEncoder encoder;
    /*发送短信验证码*/
    public Result sendSms(String mobile){
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
        redisTemplate.opsForValue().set("checkcode_"+mobile, checkcode, 1, TimeUnit.MINUTES);//默认使用 JDK 对 key 和 value 进行序列化，转成字节存入 Redis。

        //给用户发一份
        /*Map<String, String> map = new HashMap<>();
        map.put("mobile", mobile);
        map.put("checkcode", checkcode);*/
        //rabbitTemplate.convertAndSend("sms", map);
        //Object bbb = redisTemplate.opsForValue().get("checkcode_18200142543");


        //在控制台显示一份【方便测试】
        System.out.println("验证码为bbb："+checkcode);
        return new Result(false,StatusCode.OK,"成功");
    }


    /*注册用户*/
    public Result regist(User user) {
        user.setCreated(new Date());
        user.setPassword(encoder.encode(user.getPassword()));
        userMapper.insertSelective(user);
        return new Result(false,StatusCode.OK,"成功",user);
    }
}
