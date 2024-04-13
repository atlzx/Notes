package com.mybatis.example.dynamicsql.mapper;

import com.mybatis.example.dynamicsql.pojo.Employee;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface EmployeeMapper {
    Employee selectByNameAndSalary(@Param("empName") String empName,@Param("empSalary")Double empSalary);

    List<Employee> selectByNameOrSalary(@Param("empName") String empName,@Param("empSalary")Double empSalary);

    int batchInsertEmployee(List<Employee> list);

    int updateEmployeeInfoById(@Param("empName") String empName,@Param("empSalary") Double empSalary,@Param("empId") Integer empId);
    List<Employee> selectPage();
}
