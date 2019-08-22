package com.vincent.springboot.controller;

import com.vincent.springboot.dao.UserMapper;
import com.vincent.springboot.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    //依赖注入
    @Autowired
    UserMapper userMapper;

    @RequestMapping(value = "/hello")
    public User cs() {
        //调用dao层   添加注释a
        User user = userMapper.selectUserByName("mike");
        return user;
    }

}
