package com.gehua.controller;

import com.gehua.common.utils.Result;
import com.gehua.service.UserService;
import com.gehua.userPojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService userService;

    /*发送短信验证码*/
    @PostMapping("sendSms")
    public Result sendSms(@RequestBody String mobile){
        return  userService.sendSms(mobile);
    }
    /*注册用户*/
    @PostMapping("regist/{code}")
    public Result regist(@RequestBody User user, @PathVariable String code){
        return  userService.regist(user);
    }
}
