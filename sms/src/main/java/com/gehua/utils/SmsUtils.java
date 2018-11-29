package com.gehua.utils;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.gehua.config.SmsProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;



@Component
@EnableConfigurationProperties(SmsProperties.class)
public class SmsUtils {


    @Autowired
    private SmsProperties smsConfig;
    private static Logger logger = LoggerFactory.getLogger(SmsUtils.class);

    //产品名称，云通信短信api产品,无需替换
    final String product = "Dysmsapi";
    //产品域名，无需替换
    final String domain = "dysmsapi.aliyuncs.com";

    public   SendSmsResponse sendSms(String phoneNum,String SignName,String template,String tempaleteParam) throws ClientException {

            //设置超时时间
            System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
            System.setProperty("sun.net.client.defaultReadTimeout", "10000");


            //初始化ascClient,暂时不支持多region
            IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", smsConfig.getAccessKeyId(),
                    smsConfig.getAccessKeySecret());
            DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);

            IAcsClient acsClient = new DefaultAcsClient(profile);
            //组装请求对象
            SendSmsRequest request = new SendSmsRequest();
            request.setMethod(MethodType.POST);          //使用post提交

            //必填:待发送手机号。支持以逗号分隔的形式进行批量调用，批量上限为1000个手机号码,批量调用相对于单条调用及时性稍有延迟,验证码类型的短信推荐使用单条调用的方式；发送国际/港澳台消息时，接收号码格式为国际区号+号码，如“85200000000”
            request.setPhoneNumbers(phoneNum);
            //必填:短信签名
            request.setSignName(SignName);
            //必填:短信模板
            request.setTemplateCode(tempaleteParam);

            //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时
            request.setTemplateParam(tempaleteParam);


            SendSmsResponse resp = acsClient.getAcsResponse(request);
            if (!"OK".equals(resp.getCode())) {
                logger.info("短信服务错误:{}",resp.getMessage());

            }
            return  resp;


    }

}
