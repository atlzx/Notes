package com.springboot.example.mybatisplus.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.springboot.example.mybatisplus.entity.User;

import java.util.Map;

public interface UserMapper extends BaseMapper<User> {
    Map<String,Object> selectMapById(Long id);
}
