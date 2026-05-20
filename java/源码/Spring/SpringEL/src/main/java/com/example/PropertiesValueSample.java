package com.example;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@PropertySource(encoding = "UTF-8",value = "classpath:lzx.properties")
@Component
public class PropertiesValueSample {
    @Value("${name}")
    private String name;
    @Value("${age}")
    private int age;
    @Value("${description}")
    private String description;
    private Map<String,String> map=Map.of("test","测试");
    private List<Integer> list=List.of(1,2,3,4);

    public Map<String, String> getMap() {
        return map;
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }

    public List<Integer> getList() {
        return list;
    }

    public void setList(List<Integer> list) {
        this.list = list;
    }

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
