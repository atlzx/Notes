package com.example.cloud.service.impl;

import com.example.cloud.entities.Pay;
import com.example.cloud.mapper.PayMapper;
import com.example.cloud.service.PayService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PayServiceImpl implements PayService {
    @Resource
    private PayMapper payMapper;
    @Override
    public int add(Pay pay) {
        return payMapper.insertSelective(pay);
    }

    @Override
    public int update(Pay pay) {
        return payMapper.updateByPrimaryKeySelective(pay);
    }

    @Override
    public int delete(Integer id) {
        return payMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Pay getById(Integer id) {
        return payMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Pay> getAll() {
        return payMapper.selectAll();
    }


}
