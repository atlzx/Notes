package com.example.cloud.controller;

import com.example.cloud.resp.ReturnData;
import com.example.cloud.service.StorageService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/storage")
public class StorageController {
    @Resource
    private StorageService storageService;
    @PostMapping("/update")
    public ReturnData<String> updateStorage(Long productId, Integer count){
        storageService.decrease(productId, count);
        return ReturnData.ok("storage-service中扣减库存完成");
    }
}
