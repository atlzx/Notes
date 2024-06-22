package com.example.cloud.controller;

import com.example.cloud.entities.Pay;
import com.example.cloud.entities.dto.PayDTO;
import com.example.cloud.service.PayService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.BeanUtils;
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
    public String addPay(@RequestBody Pay pay){
        log.info(pay.toString());
        int i=payService.add(pay);
        return "成功插入记录，返回值"+i;
    }

    @PutMapping("/update")
    @Operation(method = "修改账单")
    public String updatePay(@RequestBody PayDTO payDTO){
        Pay pay=new Pay();
        BeanUtils.copyProperties(payDTO,pay);
        int update = payService.update(pay);
        return "成功更新记录，返回值"+update;
    }

    @DeleteMapping("/delete/{id}")
    @Operation(method = "删除账单")
    public String deletePay(@PathVariable("id") Integer id){
        int delete = payService.delete(id);
        return "成功删除记录，返回值"+delete;
    }

    @GetMapping("/get/{id}")
    @Operation(method = "根据id得到账单")
    public Pay getPayById(@PathVariable("id") Integer id){
        Pay pay = payService.getById(id);
        return pay;
    }

    @GetMapping("getAll")
    @Operation(method = "得到全部账单")
    public List<Pay> getAll(){
        List<Pay> pays = payService.getAll();
        return pays;
    }
}
