package com.springboot.example.springbootwebsocket.service;

import com.springboot.example.springbootwebsocket.pojo.User;
import com.springboot.example.springbootwebsocket.pojo.redis.UserStatus;
import com.springboot.example.springbootwebsocket.utils.CommonUtil;
import com.springboot.example.springbootwebsocket.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@Service
@Slf4j
public class InfoService {
    public UserStatus getUserInfo(Map<String,Object> parseMap) throws InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        String userId = String.valueOf((Integer)parseMap.get("userId"));
        Map value = (Map)RedisUtil.getValue(userId, RedisUtil.HASH, CommonUtil.getClassName(UserStatus.class,true));
        UserStatus instance = CommonUtil.getInstance(value, UserStatus.class);
        log.info(value.toString());
        return instance;
    }
}
