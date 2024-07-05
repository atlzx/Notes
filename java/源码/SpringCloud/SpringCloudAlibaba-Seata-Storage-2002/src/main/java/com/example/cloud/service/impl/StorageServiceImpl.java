package com.example.cloud.service.impl;

import com.example.cloud.mapper.StorageMapper;
import com.example.cloud.service.StorageService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class StorageServiceImpl implements StorageService {
    @Resource
    private StorageMapper storageMapper;
    @Override
    public void decrease(Long productId, Integer count) {
        log.info("storage-service中扣减库存开始");
        storageMapper.decrease(productId, count);
        log.info("storage-service中扣减库存结束");

    }
}
