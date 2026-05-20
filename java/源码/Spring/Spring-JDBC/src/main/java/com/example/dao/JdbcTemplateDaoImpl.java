package com.example.dao;

import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcTemplateDaoImpl implements JdbcTemplateDao{
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public int selectMoneyByID(int ID) {
        Integer result=jdbcTemplate.queryForObject(
            "select * from jdbc_test3 where id = ?",
            (rs,num)->{
                return rs.getInt("money");
            },
            ID
        );
        return result==null?-1:result;
    }

    @Override
    public int insertInfo(String name, int money) {
        return jdbcTemplate.update("insert into jdbc_test3 (money, name) values(?,?)", money, name);
    }

    @Override
    public int deleteInfo(int id) {
        return jdbcTemplate.update("delete from jdbc_test3 where id = ?",id);

    }

    @Override
    public int withDrawMoney(int id,int money) {
        return jdbcTemplate.update("update jdbc_test3 set money = money - ? where id = ?", money, id);
    }

    @Override
    public int giveMoney(int id, int money) {
        return jdbcTemplate.update("update jdbc_test3 set money = money + ? where id = ?",money,id);
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
