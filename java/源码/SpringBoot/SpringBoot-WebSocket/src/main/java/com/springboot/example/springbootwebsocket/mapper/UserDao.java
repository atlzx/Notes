package com.springboot.example.springbootwebsocket.mapper;

import com.springboot.example.springbootwebsocket.entity.User;

import java.util.List;

public interface LoginDao {
    int addUser(List<User> users);
}
