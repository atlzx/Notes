package com.springboot.example.springbootjwt.controller;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/jwt")
@CrossOrigin
@Slf4j
public class JwtController {
    @RequestMapping("/getJwt")
    public String getJWT() {
        Map<String, String> claim = new HashMap<>();
        claim.put("loginStatus", "yes");
        String Jwt=Jwts.builder()
            .signWith(SignatureAlgorithm.HS256, "sfrbhsdftjxfdgnfgujfsdxgnfgusdfbhsdysbhdshjfd")
            .setClaims(claim)
            .setExpiration(new Date(System.currentTimeMillis() + 3600 * 1000))
            .compact();
        log.info(Jwt);
        return Jwt;
    }

    @RequestMapping("/parseJwt")
    public boolean parseJwt(@RequestHeader(name = "Authorization") String Jwt) {
        log.info("-----------Jwt:{}", Jwt);
        return true;
    }
}
