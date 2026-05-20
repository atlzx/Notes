package com.springmvc.example.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import javax.sql.DataSource;


/**
     实现mapper层相关配置类需要做如下配置:
     声明为配置类
     开启组件扫描
     从properties文件内读取jdbc连接所需的相关资源
     提供数据库连接池对象、Mybatis的SqlSessionFactoryBean对象、Mybatis的MapperScannerConfigurer对象
     ...
 */

@Configuration  // 声明为配置类
@ComponentScan("com.springmvc.example.mapper")  // 开启组件扫描
@PropertySource(value = "classpath:jdbc.properties")  // 从properties文件内读取jdbc连接所需的相关资源
public class MapperConfig {
    @Value("${jdbc.url}")
    private String url;
    @Value("${jdbc.username}")
    private String username;
    @Value("${jdbc.password}")
    private String password;
    @Value("${jdbc.driver}")
    private String driver;

    @Bean
    // 提供数据库连接池相关对象，这里提供Druid连接池对象
    public DataSource dataSource(){
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setDriverClassName(driver);
        return dataSource;
    }

    @Bean
    // 提供SqlSessionFactoryBean对象，该对象可以自动帮助我们生成对应的sqlSession对象
    public SqlSessionFactoryBean sqlSessionFactoryBean(DataSource dataSource){
        // 实例化SqlSessionFactoryBean对象
        SqlSessionFactoryBean sqlSessionFactoryBean=new SqlSessionFactoryBean();
        // 设置Druid连接池
        sqlSessionFactoryBean.setDataSource(dataSource);
        Resource resource=new ClassPathResource("mybatis-config.xml");
        sqlSessionFactoryBean.setConfigLocation(resource);
        return sqlSessionFactoryBean;
    }

    @Bean
    // 提供MapperScannerConfigurer对象，该对象可以向我们提供对应mapper接口的代理对象
    public MapperScannerConfigurer mapperScannerConfigurer(){
        MapperScannerConfigurer mapperScannerConfigurer=new MapperScannerConfigurer();
        mapperScannerConfigurer.setBasePackage("com.springmvc.example.mapper");
        return mapperScannerConfigurer;
    }


}
