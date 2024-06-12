package com.springboot.example.springboottest.controller;

import com.springboot.example.springboottest.dao.UserDao;
import com.springboot.example.springboottest.entity.User;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/test")
public class MyController {
    @Resource
    private UserDao userDao;
    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }

    @RequestMapping("/getAll")
    public List<User> getAll(){
        return userDao.getAll();
    }

}
