package com.springmvc.example.controller;

import com.springmvc.example.pojo.Employee;
import com.springmvc.example.service.EmployeeService;
import com.springmvc.example.service.impl.EmployeeServiceImpl;
import com.springmvc.example.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/page")
@CrossOrigin
public class SSMController {
    @Autowired
    private EmployeeService employeeService;  // 自动注入服务层对象，需要使用接口类型接收，不然会报错

    @GetMapping("/{currentPage}/{pageSize}")
    public Result getAll(@PathVariable("currentPage") int currentPage, @PathVariable("pageSize") int pageSize){
        return employeeService.getPage(currentPage, pageSize);
    }
}
