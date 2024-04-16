package com.myProject.schedule.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
    @AllArgsConstructor
    /*
    @Getter
    @Setter
    @EqualsAndHashCode
    @ToString
    以上四个注解相当于下面一个注解
    */
    @Data
    public class SysUser implements Serializable {
        private Integer uid;
        private String userName;
        private String userPwd;
    }