package com.example.cloud.controller;

import com.example.cloud.resp.ReturnData;
import com.example.cloud.service.AccountService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/account")
@RestController
@Slf4j
public class AccountController {
    @Resource
    private AccountService accountService;
    @PostMapping("/update")
    public ReturnData<String> updateAccount(Long userId,Long money){
        accountService.decrease(userId, money);
        return ReturnData.ok("seata-account-service扣减用户余额完成");
    }
}
