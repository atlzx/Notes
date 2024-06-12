package com.springboot.example.springboottest.dao;

import com.springboot.example.springboottest.entity.User;
import lombok.Data;

import java.util.List;


public interface UserDao {
    List<User> getAll();
}
