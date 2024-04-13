package com.mybatis.example.multitablemapping.mapper;

import com.mybatis.example.multitablemapping.pojo.Customer;
import com.mybatis.example.multitablemapping.pojo.Order;

public interface CustomerMapper {
    Customer selectCustomerByCustomerId(Integer orderId);


}
