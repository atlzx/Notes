package com.springboot.example.springbootwebsocket.pojo.redis;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserStatus {
    private Integer isLogin;
    private Integer userId;
    private String userName;
}
