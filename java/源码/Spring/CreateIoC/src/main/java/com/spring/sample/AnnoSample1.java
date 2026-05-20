package com.spring.sample;

@Bean
public class AnnoSample1 {
    @DependencyInject
    private AnnoSample2 anno;

    public void run1(){
        anno.run2();
    }
}
