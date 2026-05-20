package com.springboot.example.mybatisplus.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.springboot.example.mybatisplus.entity.User;
import com.springboot.example.mybatisplus.mapper.UserMapper;
import com.springboot.example.mybatisplus.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
