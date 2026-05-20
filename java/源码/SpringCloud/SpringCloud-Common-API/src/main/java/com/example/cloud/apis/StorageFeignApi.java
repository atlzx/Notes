package com.example.cloud.apis;


import com.example.cloud.resp.ReturnData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "seata-storage-service")
public interface StorageFeignApi {
    @PostMapping(value = "/storage/update")
    ReturnData<String> decrease(@RequestParam("productId") Long productId, @RequestParam("count") Integer count);
}
