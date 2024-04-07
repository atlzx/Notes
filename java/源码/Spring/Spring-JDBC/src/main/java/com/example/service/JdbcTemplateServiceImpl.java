package com.example.service;

import com.example.dao.JdbcTemplateDaoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

@Service
// 下面规定事务处理规则:可以执行任意sql语句、超时3s后回滚、隔离级别默认、传播行为是没有新建，有就加入。回滚策略是遇到除 ArithmeticException (数值运算异常) 之外的情况时才回滚
@Transactional(readOnly = false,timeout = 3,isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED, noRollbackFor = ArithmeticException.class)
public class JdbcTemplateServiceImpl implements JdbcTemplateService{
    @Autowired
    private JdbcTemplateDaoImpl jdbcTemplateDao;

    public void setJdbcTemplateDao(JdbcTemplateDaoImpl jdbcTemplateDao) {
        this.jdbcTemplateDao = jdbcTemplateDao;
    }

    @Override
    public void transfer1(int subID, int addID, int money) {
        jdbcTemplateDao.withDrawMoney(subID,money);
        jdbcTemplateDao.giveMoney(addID,money);
    }

    @Override
    @Transactional(readOnly = false,timeout = 3,isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED, noRollbackFor = ArithmeticException.class)
    public void transfer2(int subID,int addID,int money) {
        try {
            TimeUnit.SECONDS.sleep(5);
            transfer1(subID, addID, money);  //  这个大概是算的从得到连接开始到最后一次执行数据库操作中连接的间隔时长是否超过指定秒
        } catch (InterruptedException e) {
            throw new RuntimeException("运行出错");
        }
    }

    @Override
    public void transfer3() {
        throw new ArithmeticException("故意报错");
    }

}
