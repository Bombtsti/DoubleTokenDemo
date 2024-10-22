package com.example.demo.controller;


import com.example.demo.pojo.User;
import com.example.demo.service.UserService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.result.Result;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public Result<?> getAllUser(){
        return userService.getAllUser();
    }

    @PostMapping("/login")
    public Result<?> login(@RequestBody User user){
        return userService.login(user);
    }

    @GetMapping("/refreshToken")
    public Result<?> refreshToken(String refreshToken){
        return userService.refreshToken(refreshToken);
    }
}
