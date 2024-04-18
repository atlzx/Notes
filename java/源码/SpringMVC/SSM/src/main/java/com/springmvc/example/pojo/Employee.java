package com.springmvc.example.pojo;

import lombok.Data;
import org.apache.ibatis.type.Alias;

@Data
public class Employee {
    private String firstName;
    private String lastName;
    private String email;
}
