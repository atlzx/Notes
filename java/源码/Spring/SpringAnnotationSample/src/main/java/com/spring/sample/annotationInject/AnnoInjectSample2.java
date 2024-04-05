package com.spring.sample.annotationInject;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

@Component
public class AnnoInjectSample2 {
    /*
        Resource注解也可以用来进行自动装配
        它会先寻找我们为注解指定的name值相匹配的对象进行注入，如果指定的name不存在，会报错
        如果未指定将寻找与其作用的属性的名称相同的对象进行注入
        如果还未找到，那么将寻找类型相同的对象注入
     */
    @Resource
    private CommonInject commonInject;
    @Resource(name = "commonInject")
    private CommonInject co;

    @Resource
    private CommonInject c;

    public CommonInject getCommonInject() {
        return commonInject;
    }

    public void setCommonInject(CommonInject commonInject) {
        this.commonInject = commonInject;
    }

    public CommonInject getCo() {
        return co;
    }

    public void setCo(CommonInject co) {
        this.co = co;
    }

    public CommonInject getC() {
        return c;
    }

    public void setC(CommonInject c) {
        this.c = c;
    }
}
