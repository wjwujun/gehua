package com.gehua.controller;

import com.gehua.common.utils.Result;
import com.gehua.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("sendSms")
    public Result sendSms(@RequestBody String mobile){
        return  userService.sendSms(mobile);
    }

}
