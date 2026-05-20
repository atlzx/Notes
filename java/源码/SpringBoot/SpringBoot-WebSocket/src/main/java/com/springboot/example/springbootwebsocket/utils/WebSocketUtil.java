package com.springboot.example.springbootwebsocket.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.springboot.example.springbootwebsocket.entity.WebSocketMessage;
import jakarta.annotation.Resource;

import org.springframework.stereotype.Component;


import java.util.Set;


public class WebSocketUtil {
    public static String getSystemMessage(String message,String toUser,Long timeStamp) throws JsonProcessingException {
        return JsonUtil.entityToJson(new WebSocketMessage(1,"System",toUser,message,timeStamp));
    }
}
