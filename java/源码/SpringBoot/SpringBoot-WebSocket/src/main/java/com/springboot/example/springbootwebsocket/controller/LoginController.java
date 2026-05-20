package com.springboot.example.springbootwebsocket.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.springboot.example.springbootwebsocket.entity.Result;
import com.springboot.example.springbootwebsocket.service.LoginService;
import com.springboot.example.springbootwebsocket.utils.CommonConstantUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/login")
@CrossOrigin
@Slf4j
public class LoginController {
    @Resource
    private LoginService loginService;


    @PostMapping
    public Result login(@RequestBody HashMap<String,Object> requestMap, HttpSession session) throws JsonProcessingException {

        Result result=loginService.login(requestMap.get("userName").toString(),requestMap.get("password").toString());
        return result;
    }


}
