package com.springboot.example.mybatisplus.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.springboot.example.mybatisplus.entity.SlaveUser;
import com.springboot.example.mybatisplus.mapper.SlaveUserMapper;
import com.springboot.example.mybatisplus.service.SlaveUserService;
import org.springframework.stereotype.Service;

@Service
@DS("slave")
public class SlaveUserServiceImpl extends ServiceImpl<SlaveUserMapper, SlaveUser> implements SlaveUserService {
}
