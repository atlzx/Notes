package com.example;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@PropertySource(encoding = "UTF-8",value = "classpath:lzx.properties")
@Component
public class PropertiesValueSample {
    @Value("${name}")
    private String name;
    @Value("${age}")
    private int age;
    @Value("${description}")
    private String description;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
