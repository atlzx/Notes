package com.springboot.example.mybatisplus.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.springboot.example.mybatisplus.enums.SexEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("user")
public class MasterUser {
    private Long id;
    private String name;
    private Integer age;
    private String email;
    private SexEnum sex;
}
