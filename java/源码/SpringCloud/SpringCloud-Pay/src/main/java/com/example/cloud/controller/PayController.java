package com.example.cloud.controller;

import com.example.cloud.entities.Pay;
import com.example.cloud.entities.dto.PayDTO;
import com.example.cloud.resp.ReturnData;
import com.example.cloud.service.PayService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/pay")
@Tag(name = "支付微服务模块",description = "支付CRUD")
public class PayController {
    @Resource
    private PayService payService;
    @PostMapping("/add")
    @Operation(method = "添加账单")
    public ReturnData<Integer> addPay(@RequestBody PayDTO payDTO){
        log.info(payDTO.toString());
        Pay pay=new Pay();
        BeanUtils.copyProperties(payDTO,pay);
        int i=payService.add(pay);
        return ReturnData.ok(i);
    }

    @PutMapping("/update")
    @Operation(method = "修改账单")
    public ReturnData<Integer> updatePay(@RequestBody PayDTO payDTO){
        Pay pay=new Pay();
        BeanUtils.copyProperties(payDTO,pay);
        int update = payService.update(pay);
        return ReturnData.ok(update);
    }

    @DeleteMapping("/delete/{id}")
    @Operation(method = "删除账单")
    public ReturnData<Integer> deletePay(@PathVariable("id") Integer id){
        int delete = payService.delete(id);
        return ReturnData.ok(delete);
    }

    @GetMapping("/get/{id}")
    @Operation(method = "根据id得到账单")
    public ReturnData<Pay> getPayById(@PathVariable("id") Integer id){
        Pay pay = payService.getById(id);
        return ReturnData.ok(pay);
    }

    @GetMapping("getAll")
    @Operation(method = "得到全部账单")
    public ReturnData<List<Pay>> getAll(){
        List<Pay> pays = payService.getAll();
        return ReturnData.ok(pays);
    }
}
