package com.example.cloud.controller;

import com.example.cloud.entities.Order;
import com.example.cloud.resp.ReturnData;
import com.example.cloud.service.OrderService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/order")
public class OrderController {
    @Resource
    private OrderService orderService;
    @GetMapping("/add")
    public ReturnData<String> addOrder(@RequestBody Order order){
        orderService.create(order);
        return ReturnData.ok("创建订单完成");
    }
}
