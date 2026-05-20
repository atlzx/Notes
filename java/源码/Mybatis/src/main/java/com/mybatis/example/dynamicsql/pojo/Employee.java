package com.mybatis.example.dynamicsql.pojo;

import lombok.Data;
import org.apache.ibatis.type.Alias;

@Data
public class Employee {
    private Integer empId;
    private String empName;
    private Double empSalary;

    public Employee(Integer empId, String empName, Double empSalary) {
        this.empId = empId;
        this.empName = empName;
        this.empSalary = empSalary;
    }
}
