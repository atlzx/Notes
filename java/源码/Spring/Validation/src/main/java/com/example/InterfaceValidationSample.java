package com.example;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class InterfaceValidationSample implements Validator {
    @Override
    // supports方法用来筛选支持的类
    public boolean supports(Class<?> clazz) {
        return People.class.equals(clazz);
    }

    @Override
    // validate方法用来执行校验逻辑
    // target 参数是校验的对应类对象实例
    // errors 参数是错误集合
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmpty(errors,"name","name.empty","名字不能为空");
        People people=(People)target;
        if(people.getAge()<0){
            errors.rejectValue("age","年龄太小");
        }else if(people.getAge()>150){
            errors.rejectValue("age","年龄太大");
        }
    }
}
