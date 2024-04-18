package com.springmvc.example.mapper;

import com.springmvc.example.pojo.Employee;
import org.apache.ibatis.type.Alias;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Alias("EmployeeMapper")
public interface EmployeeMapper {
    List<Employee> selectAll();
}
