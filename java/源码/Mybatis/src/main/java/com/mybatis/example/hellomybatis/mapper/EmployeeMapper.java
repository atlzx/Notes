package com.mybatis.example.hellomybatis.mapper;

import com.mybatis.example.hellomybatis.pojo.Employee;

public interface EmployeeMapper {
    Employee selectEmployeeById(Integer empId);

    Integer deleteEmployeeById(Integer empId);
}
