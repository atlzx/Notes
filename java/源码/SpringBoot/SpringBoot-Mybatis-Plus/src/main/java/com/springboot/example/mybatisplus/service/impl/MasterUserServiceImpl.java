package com.springboot.example.mybatisplus.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.springboot.example.mybatisplus.entity.MasterUser;
import com.springboot.example.mybatisplus.mapper.MasterUserMapper;
import com.springboot.example.mybatisplus.service.MasterUserService;
import org.springframework.stereotype.Service;

@Service
@DS("master")
public class MasterUserServiceImpl extends ServiceImpl<MasterUserMapper, MasterUser> implements MasterUserService {

}
