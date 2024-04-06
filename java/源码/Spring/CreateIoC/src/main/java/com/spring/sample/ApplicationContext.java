package com.spring.sample;

public interface ApplicationContext {
    <T> T getBean(Class<T> clazz);


}
