package com.springboot.example.mybatisplus.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("user")
public class SlaveUser {
    private Long id;
    private String name;
    private Integer age;
    private String email;
}
