package com.example.cloud.apis.fallback;

import com.example.cloud.apis.SentinelPayFeignApi;
import com.example.cloud.resp.ReturnData;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class SentinelPayFeignApiFallback implements SentinelPayFeignApi {
    @Override
    public ReturnData<String> getInfo() {
        return ReturnData.fail(HttpStatus.BAD_REQUEST.value());
    }
}
