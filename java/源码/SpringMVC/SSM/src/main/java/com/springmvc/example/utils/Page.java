package com.springmvc.example.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data  // 提供一系列方法
@NoArgsConstructor  // 提供无参构造
@AllArgsConstructor  // 提供全参构造
public class Page {
    private Integer currentPage;
    private Integer pageSize;
    private Integer dataCount;
    private Long totalCount;
    private List list;
}
