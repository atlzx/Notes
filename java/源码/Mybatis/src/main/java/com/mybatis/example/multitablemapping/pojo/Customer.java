package com.mybatis.example.multitablemapping.pojo;

import lombok.Data;

import java.util.List;

@Data
public class Customer {
    private Integer customerId;
    private String customerName;
    private List<Order> list;
}
