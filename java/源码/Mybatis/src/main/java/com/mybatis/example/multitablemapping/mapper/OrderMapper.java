package com.mybatis.example.multitablemapping.mapper;

import com.mybatis.example.multitablemapping.pojo.Order;

public interface OrderMapper {
    Order selectOrderByOrderId(Integer customerId);
}
