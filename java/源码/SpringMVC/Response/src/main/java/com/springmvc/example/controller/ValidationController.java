package com.springmvc.example.controller;

import com.springmvc.example.pojo.User;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestController
@RequestMapping("/valid")
public class ValidationController {
    @RequestMapping("/v1")
    // 在想校验的实体类对象前加上@Validated注解
    // BindingResult对象必须紧紧跟在被校验对象后面
    public User validation(@RequestBody @Validated User user, BindingResult result){
        // 判断是否校验有错误
        if (result.hasErrors()) {
            // 得到校验中的错误，并遍历输出错误信息
            List<ObjectError> list = result.getAllErrors();
            for(var i:list){
                System.out.println(i.toString());
            }
            return null;
        }else{
            // 如果校验成功，那么直接返回对象
            return user;
        }
    }
}
