package com.springmvc.example.service;

import com.springmvc.example.utils.Result;


public interface EmployeeService {
    Result getPage(int currentPage, int pageSize);
}
