package com.spring.sample.lifecycle;

public class LifeCycleSample {
    private String name;
    public LifeCycleSample() {
        System.out.println("第一步:构造器执行");
    }

    public void setName(String name) {
        System.out.println("第二步，给对象的属性赋值");
        this.name = name;
    }

    public void init(){
        System.out.println("第四步，对象的初始化");
    }

    public void destroy(){
        System.out.println("第七步，对象的销毁");
    }
}
