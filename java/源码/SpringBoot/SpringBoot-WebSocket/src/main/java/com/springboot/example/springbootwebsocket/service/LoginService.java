package com.springboot.example.springbootwebsocket.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.springboot.example.springbootwebsocket.entity.Result;
import com.springboot.example.springbootwebsocket.pojo.User;
import com.springboot.example.springbootwebsocket.mapper.UserDao;
import com.springboot.example.springbootwebsocket.pojo.redis.UserStatus;
import com.springboot.example.springbootwebsocket.utils.CommonConstantUtil;
import com.springboot.example.springbootwebsocket.utils.JsonUtil;
import com.springboot.example.springbootwebsocket.utils.JwtUtil;
import com.springboot.example.springbootwebsocket.utils.RedisUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Slf4j
public class LoginService {
    @Resource
    private UserDao userDao;
    @Resource
    private CommonConstantUtil commonConstantUtil;


    public Result login(String userName,String password) throws JsonProcessingException{
        User user = userDao.getUserByNameAndPassword(userName, password);
        updateRedisLoginStatus(user);
        String jwt = JwtUtil.getJwt(commonConstantUtil.getJwtKey(),user,1000*60*60*24*7);
        if(jwt!=null){
            user.setUserPassword(null);
            return Result.getResult(user,Result.SUCCESS_STATUS_CODE,Result.DEFAULT_SUCCESS_MESSAGE,jwt);
        }else{
            return Result.getResult(null,Result.LOGIC_ERROR_STATUS_CODE,"用户名或密码错误",null);
        }
    }



    public void updateRedisLoginStatus(User user) throws JsonProcessingException {
        UserStatus userStatus=new UserStatus();
        userStatus.setUserName(user.getUserName());
        userStatus.setUserId(user.getUserId());
        userStatus.setIsLogin(1);
        RedisUtil.update(String.valueOf(userStatus.getUserId()),userStatus);
    }
}
