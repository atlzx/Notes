package com.springmvc.example.service;

import com.springmvc.example.pojo.Employee;
import com.springmvc.example.utils.Result;
import org.springframework.stereotype.Service;

import java.util.List;


public interface EmployeeService {
    Result getPage(int currentPage, int pageSize);
}
