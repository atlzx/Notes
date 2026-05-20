package com.example.service;

public interface JdbcTemplateService {
    void transfer1(int subID,int addID,int money);
    void transfer2(int subID,int addID,int money);
    void transfer3();
}
