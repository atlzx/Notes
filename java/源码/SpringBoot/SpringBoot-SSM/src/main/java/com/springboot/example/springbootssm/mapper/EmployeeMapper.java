package com.springboot.example.springbootssm.mapper;

import com.springboot.example.springbootssm.pojo.Employee;

public interface EmployeeMapper {
    Employee getEmpById(Integer empId);
}
