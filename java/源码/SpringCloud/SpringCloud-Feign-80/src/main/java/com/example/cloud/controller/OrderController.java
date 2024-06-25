package com.example.cloud.controller;

import com.example.cloud.apis.PayFeignApi;
import com.example.cloud.entities.Pay;
import com.example.cloud.entities.dto.PayDTO;
import com.example.cloud.resp.ReturnData;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/feign")
public class OrderController {
    @Resource
    private PayFeignApi payFeignApi;

    @PostMapping("/pay/add")
    public ReturnData<Integer> addPay(@RequestBody PayDTO payDTO){
        return payFeignApi.addPay(payDTO);
    }
    @PutMapping("/pay/update")
    public ReturnData<Integer> updatePay(@RequestBody PayDTO payDTO){
        return payFeignApi.updatePay(payDTO);
    }
    @DeleteMapping("/pay/delete/{id}")
    public ReturnData<Integer> deletePay(@PathVariable("id") Integer id){
        return payFeignApi.deletePay(id);
    }
    @GetMapping("/pay/get/{id}")
    public ReturnData<Pay> getPayById(@PathVariable("id") Integer id){
        return payFeignApi.getPayById(id);
    }
    @GetMapping("/pay/getAll")
    public ReturnData<List<Pay>> getAll(){
        return payFeignApi.getAll();
    }
    @GetMapping("/pay/get/info")
    public ReturnData<String> getInfo(){
        return payFeignApi.getInfo();
    }


}
