package com.example.cloud.exception;

import cn.hutool.core.date.DateUtil;
import com.example.cloud.resp.ReturnData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionProcessor {
    @ExceptionHandler(value = Exception.class)
    public ReturnData<String> defaultExceptionHandler(Exception e){
        log.error("全局异常信息:{}", e.toString(), e);
        log.info("当前时间:{}", DateUtil.now());
        return ReturnData.ok(e.toString());
    }
}
