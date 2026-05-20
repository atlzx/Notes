package com.springmvc.example.mapper;

import com.springmvc.example.pojo.Employee;

import java.util.List;

public interface EmployeeMapper {
    List<Employee> selectAll();
}
