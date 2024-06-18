package com.example.cloud.controller;

import com.example.cloud.entities.Pay;
import com.example.cloud.entities.dto.PayDTO;
import com.example.cloud.service.PayService;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/pay")
public class PayController {
    @Resource
    private PayService payService;
    @PostMapping("/add")
    public String addPay(@RequestBody Pay pay){
        log.info(pay.toString());
        int i=payService.add(pay);
        return "成功插入记录，返回值"+i;
    }

    @PostMapping("/update")
    public String updatePay(@RequestBody PayDTO payDTO){
        Pay pay=new Pay();
        BeanUtils.copyProperties(payDTO,pay);
        int update = payService.update(pay);
        return "成功更新记录，返回值"+update;
    }

    @DeleteMapping("/delete/{id}")
    public String deletePay(@PathVariable("id") Integer id){
        int delete = payService.delete(id);
        return "成功删除记录，返回值"+delete;
    }

    @GetMapping("/get/{id}")
    public Pay getPayById(@PathVariable("id") Integer id){
        Pay pay = payService.getById(id);
        return pay;
    }

    @GetMapping("getAll")
    public List<Pay> getAll(){
        List<Pay> pays = payService.getAll();
        return pays;
    }
}
