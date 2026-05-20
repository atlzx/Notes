package com.springmvc.example.config;


import org.springframework.context.annotation.*;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;


/**
         实现service层相关配置类需要做如下配置:
         声明为配置类
         开启组件扫描
         提供TransactionManager类的事务管理对象，并开启事务管理
         开启AspectJ自动代理以支持AOP操作
         ...
 */
@Configuration  //  声明为配置类
@ComponentScan("com.springmvc.example.service")  // 开启组件扫描
@EnableAspectJAutoProxy  // 开启AspectJ自动代理以支持AOP操作
@EnableTransactionManagement  // 开启Spring的事务管理
public class ServiceConfig {

    @Bean  // 向IOC容器提供事务管理对象
    public TransactionManager transactionManager(DataSource dataSource){
        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
        dataSourceTransactionManager.setDataSource(dataSource);
        return dataSourceTransactionManager;
    }

}
