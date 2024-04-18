package com.springmvc.example.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.github.pagehelper.PageInterceptor;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.stdout.StdOutImpl;
import org.apache.ibatis.session.AutoMappingBehavior;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.core.io.ClassPathResource;

import javax.sql.DataSource;
import java.util.Properties;
import java.util.logging.Logger;

@Configuration
@PropertySource(value = "classpath:jdbc.properties")
public class MapperConfigNew {
    private String url;
    private String username;
    private String password;
    private String driver;

    @Bean
    // 提供数据库连接池相关对象，这里提供Druid连接池对象
    public DataSource dataSource(
        // 使用@Value注解把从properties文件内读取到的值赋给相关属性，需要使用${}插值表达式
        // 如果在方法外声明属性，再在方法内使用属性，那么得到的值为空
        // 出现该情况的原因是被@Bean注解作用的方法执行顺序早于被@Value注解作用的属性赋值顺序，因此方法执行时属性还未被注入
        // 可以直接在方法参数列表传入参数，再使用@Value注入，这样就可以得到值了
        @Value("${jdbc.url}") String url,
        @Value("${jdbc.username}") String username,
        @Value("${jdbc.password}") String password,
        @Value("${jdbc.driver}") String driver

    ){
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setDriverClassName(driver);
        // 顺便给属性赋个值
        this.url=url;
        this.username=username;
        this.password=password;
        this.driver=driver;
        return dataSource;
    }

    @Bean
    public SqlSessionFactoryBean sqlSessionFactoryBean(DataSource dataSource){
        // 实例化SqlSessionFactoryBean对象
        SqlSessionFactoryBean sqlSessionFactoryBean=new SqlSessionFactoryBean();
        // 设置Druid连接池
        sqlSessionFactoryBean.setDataSource(dataSource);

        org.apache.ibatis.session.Configuration configuration=new org.apache.ibatis.session.Configuration();
        // 开启数据库字段名的自动小驼峰映射
        configuration.setMapUnderscoreToCamelCase(true);
        // 开启resultMap的自动映射
        configuration.setAutoMappingBehavior(AutoMappingBehavior.FULL);
        // 开启日志功能
        configuration.setLogImpl(StdOutImpl.class);

        // 将设置项配置进sqlSessionFactoryBean内
        sqlSessionFactoryBean.setConfiguration(configuration);
        // 给指定的包内的类起别名，注意需要起别名的是pojo包内的类，不是mapper包内的类
        sqlSessionFactoryBean.setTypeAliasesPackage("com.springmvc.example.pojo");

        // 配置pageHelper插件
        PageInterceptor pageInterceptor=new PageInterceptor();
        Properties properties=new Properties();
        properties.put("helperDialect","mysql");
        pageInterceptor.setProperties(properties);
        sqlSessionFactoryBean.addPlugins(pageInterceptor);

        // 添加对应的xml映射文件
        sqlSessionFactoryBean.addMapperLocations(new ClassPathResource("com/springmvc/example/mapper/EmployeeMapper.xml"));

        return sqlSessionFactoryBean;
    }

    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer(){
        MapperScannerConfigurer mapperScannerConfigurer=new MapperScannerConfigurer();
        mapperScannerConfigurer.setBasePackage("com.springmvc.example.mapper");
        return mapperScannerConfigurer;
    }
}
