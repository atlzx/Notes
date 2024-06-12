package com.springboot.example.springbootwebsocket.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;


public class JsonUtil {
    public static ObjectMapper objectMapper;

    public static <T> T jsonToEntity(String str,Class<T> clazz) throws JsonProcessingException{
        return objectMapper.readValue(str,clazz);
    }

    public static String entityToJson(Object entity) throws JsonProcessingException{
        return objectMapper.writeValueAsString(entity);
    }
    public static Map entityToMap(Object entity) throws JsonProcessingException {
        return objectMapper.readValue(objectMapper.writeValueAsString(entity),Map.class);
    }
}
