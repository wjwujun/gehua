package com.gehua.mq;

import com.aliyuncs.exceptions.ClientException;
import com.gehua.common.utils.JsonUtils;
import com.gehua.config.SmsProperties;
import com.gehua.utils.SmsUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
@EnableConfigurationProperties(SmsProperties.class)
public class SmsListener {
    @Autowired
    private SmsUtils smsUtils;
    @Autowired
    private  SmsProperties sms;
    @Autowired
    private StringRedisTemplate redisTemplate;

    private static final String key_prefix="sms_";

    private static Logger logger = LoggerFactory.getLogger(SmsUtils.class);
    /*发送短信验证码*/
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "sms.queue",durable = "true"),
            exchange = @Exchange(name = "sms.exchange",type = ExchangeTypes.TOPIC),
            key ="sms.verfity")
    )
    public void listenInsert(Map<String,String> msg) throws ClientException {
        System.out.println(msg.get("phone"));
        System.out.println(msg.get("code"));

        if(CollectionUtils.isEmpty(msg)){
            return;
        }
         String phone=msg.remove("phone");
        if(StringUtils.isBlank(phone)){
            return;
        }
        //存入key
        String phone_str=key_prefix+phone;

        //redis，查看1分钟时间,如果没超过1分钟不能再次发送
        String lastTime = redisTemplate.opsForValue().get(phone_str);
        if(StringUtils.isNotBlank(lastTime)){
            Long last = Long.valueOf(lastTime);
            if(System.currentTimeMillis()-last<60000){
                return ;
            }
        }
        /*发送手机验证码*/
        smsUtils.sendSms(phone,sms.getSignName(),sms.getVerifyCodeTemplate(), JsonUtils.serialize(msg));

        //发送成功后,写入redis,指定过期时间为1min
        redisTemplate.opsForValue().set(phone_str,String.valueOf(System.currentTimeMillis()),1,TimeUnit.MINUTES);

    }




}
