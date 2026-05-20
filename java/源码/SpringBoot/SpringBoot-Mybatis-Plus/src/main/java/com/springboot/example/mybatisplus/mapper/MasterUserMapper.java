package com.springboot.example.mybatisplus.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.springboot.example.mybatisplus.entity.MasterUser;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MasterUserMapper extends BaseMapper<MasterUser> {
}
