package com.springboot.example.mybatisplus.entity;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@TableName(value = "t_product")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @TableId
    private Long id;
    private String name;
    private Integer price;
    @Version  // 该注解用来声明字段是乐观锁版本字段
    private Integer version;
}
