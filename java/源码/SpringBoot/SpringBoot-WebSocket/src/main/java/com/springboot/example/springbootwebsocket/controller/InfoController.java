package com.springboot.example.springbootwebsocket.controller;

import com.springboot.example.springbootwebsocket.entity.Result;
import com.springboot.example.springbootwebsocket.pojo.redis.UserStatus;
import com.springboot.example.springbootwebsocket.service.InfoService;
import com.springboot.example.springbootwebsocket.utils.CommonConstantUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.InvocationTargetException;
import java.util.Enumeration;
import java.util.Map;

@RestController
@RequestMapping("/info")
@CrossOrigin
@Slf4j
public class InfoController {
    @Resource
    private InfoService infoService;
    @Resource
    private CommonConstantUtil commonConstantUtil;
    @GetMapping("/userInfo")
    public Result getUserInfo(HttpServletRequest req) throws InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        Map<String,Object> parseMap = (Map<String,Object>)req.getAttribute(commonConstantUtil.getJwtParseName());
        UserStatus userStatus = infoService.getUserInfo(parseMap);
        return Result.getResult(userStatus,Result.SUCCESS_STATUS_CODE,Result.DEFAULT_SUCCESS_MESSAGE,null);
    }
}
