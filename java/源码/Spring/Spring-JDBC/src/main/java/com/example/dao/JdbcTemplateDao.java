package com.example.dao;

public interface JdbcTemplateDao {
    int withDrawMoney(int id,int money);

    int giveMoney(int ID,int money);

    int selectMoneyByID(int ID);

    int insertInfo(String name,int money);

    int deleteInfo(int ID);
}
