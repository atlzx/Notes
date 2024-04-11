package com.mybatis.example.hellomybatis.mapper;

import com.mybatis.example.hellomybatis.pojo.Employee;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface EmployeeMapper {
    Employee selectEmployeeById(Integer empId);

    Integer deleteEmployeeById(Integer empId);

    // 使用@Param注解使Mybatis能够识别出来多个参数
    List<Employee> selectSlicedEmployee(@Param("begin") Integer begin, @Param("end") Integer end);

    Map<String,String> selectInfoByMap(Map<String,Object> map);


}
