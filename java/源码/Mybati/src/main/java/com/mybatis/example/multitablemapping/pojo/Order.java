package com.mybatis.example.multitablemapping.pojo;


import lombok.Data;

@Data
public class Order {
    private Integer orderId;
    private String orderName;
    private Customer customer;
}
