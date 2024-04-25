package com.springboot.example.springbootthymeleaf.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Person {
    private String id;
    private String name;
    private Integer age;
    private String desc;
}
