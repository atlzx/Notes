package com.springboot.example.springbootssm.controller;

import com.springboot.example.springbootssm.mapper.EmployeeMapper;
import com.springboot.example.springbootssm.pojo.Employee;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ssm")
@Slf4j
public class SSMController {
    @Autowired
    EmployeeMapper employeeMapper;

    @GetMapping(value = "/emp/{id}")
    public Employee getEmpById(@PathVariable("id") Integer id){
        log.info("--------------------------"+id);
        return employeeMapper.getEmpById(id);
    }
}
