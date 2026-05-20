package com.springmvc.example.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.springmvc.example.mapper.EmployeeMapper;
import com.springmvc.example.pojo.Employee;
import com.springmvc.example.service.EmployeeService;
import com.springmvc.example.utils.Page;
import com.springmvc.example.utils.Result;
import com.springmvc.example.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeMapper employeeMapper;

    @Override
    public Result getPage(int currentPage, int pageSize) {
        PageHelper.startPage(currentPage,pageSize);
        List<Employee> employees = employeeMapper.selectAll();
        PageInfo<Employee> info=new PageInfo<>(employees);
        Page page=new Page(currentPage,pageSize,info.getSize(),info.getTotal(),info.getList());
        return Result.ok(page);
    }
}
