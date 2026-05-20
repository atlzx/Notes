package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CheckImpl implements Check{
    @Autowired
    private JdbcTemplateService jdbcTemplateService;

    public void setJdbcTemplateService(JdbcTemplateService jdbcTemplateService) {
        this.jdbcTemplateService = jdbcTemplateService;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void batchExecute(int subID,int[] addID,int money) {
        for(int i=0;i<addID.length;i++){
            jdbcTemplateService.transfer1(subID,addID[i],money);
        }
    }
}
