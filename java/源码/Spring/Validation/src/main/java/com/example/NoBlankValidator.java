package com.example;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.ConstraintViolation;

// 实现的校验器需要实现ConstraintValidator接口，它的两个泛型分别表示该校验器作用的是被哪个注解作用的属性，以及该属性的类型
public class NoBlankValidator implements ConstraintValidator<NoBlank,String> {

    @Override
    public void initialize(NoBlank constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    // isValid用来指定校验逻辑，如果通过校验返回true
    public boolean isValid(String s, ConstraintValidatorContext context) {
        if(s==null||s.contains(" ")){
            //获取默认提示信息
            String defaultConstraintMessageTemplate = context.getDefaultConstraintMessageTemplate();
            System.out.println("default message :" + defaultConstraintMessageTemplate);
            //禁用默认提示信息
            context.disableDefaultConstraintViolation();
            //设置提示语
            context.buildConstraintViolationWithTemplate("不能包含空格").addConstraintViolation();
            return false;
        }
        return true;
    }
}
