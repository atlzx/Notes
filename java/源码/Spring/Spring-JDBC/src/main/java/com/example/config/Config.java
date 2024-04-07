package com.example.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
//@EnableTransactionManagement
@ComponentScan("com.example")
public class Config {
//
//    @Bean(value = "dataSource")
//    public DataSource getDataSource(){
//        DruidDataSource dataSource=new DruidDataSource();
//        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
//        dataSource.setUrl("jdbc:mysql://localhost:3306/atguigudb");
//        dataSource.setUsername("root");
//        dataSource.setPassword("1928564318asd");
//        return dataSource;
//    }
//    @Bean(value = "jdbcTemplate")
//
//    public JdbcTemplate getJdbcTemplate(DataSource dataSource){
//
//        return new JdbcTemplate(dataSource);
//
//    }
//
//    // 这是用于进行事务管理的Bean,如果未使用事务，可以不写
//    @Bean(value = "transactionManager")
//    public TransactionManager getTransactionManager(DataSource dataSource){
//        return new DataSourceTransactionManager(dataSource);
//    }

}
