package com.example.cloud.apis;

import com.example.cloud.entities.Pay;
import com.example.cloud.entities.dto.PayDTO;
import com.example.cloud.resp.ReturnData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(value = "cloud-pay-service")
public interface PayFeignApi {
    @PostMapping("/pay/add")
    ReturnData<Integer> addPay(PayDTO payDTO);

    @PutMapping("/pay/update")
    ReturnData<Integer> updatePay(@RequestBody PayDTO payDTO);

    @DeleteMapping("/pay/delete/{id}")
    ReturnData<Integer> deletePay(@PathVariable("id") Integer id);

    @GetMapping("/pay/get/{id}")
    ReturnData<Pay> getPayById(@PathVariable("id") Integer id);

    @GetMapping("/pay/getAll")
    ReturnData<List<Pay>> getAll();

    @GetMapping("/pay/get/info")
    ReturnData<String> getInfo();
}
