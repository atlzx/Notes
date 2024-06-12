package com.springboot.example.springbootwebsocket.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.springboot.example.springbootwebsocket.pojo.User;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Data
public class JwtUtil {
    public static String getJwt(String jwtKey,Object content,long expirationTime) throws JsonProcessingException {
        if(content!=null){
            Map claim = JsonUtil.entityToMap(content);
            SecretKey key = Keys.hmacShaKeyFor(jwtKey.getBytes()); // 得到私钥对象
            String jwt = Jwts.builder()  // 得到构建器
                .claims(claim)  // 设置载荷(Payload)
                .signWith(key, Jwts.SIG.HS256)  // 设置私玥以及加密算法
                .expiration(new Date(System.currentTimeMillis() + expirationTime))  // 设置过期时间
                .compact();  // 生成jwt
            return jwt;
        }else{
            return null;
        }
    }

    public static Map<String,Object> parseJwt(String jwt,String jwtKey){
        byte[] bytes = jwtKey.getBytes();
        SecretKey key = Keys.hmacShaKeyFor(bytes);
        Jwt<?, ?> parse = Jwts.parser().verifyWith(key).build().parse(jwt);
        Map<String,Object> payload = (Map<String,Object>)parse.getPayload();
        return payload;
    }


}
