package test;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.alibaba.druid.pool.DruidPooledConnection;
import org.junit.Test;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

public class JDBCTest {
    @Test
    public void test1() throws Exception{
        Properties properties=new Properties();
        properties.load(new FileInputStream("src/druid/info.properties"));
        DataSource dataSource= DruidDataSourceFactory.createDataSource(properties);
        Connection connection = dataSource.getConnection();
        PreparedStatement preSta = connection.prepareStatement("select * from jdbc_test1");
        ResultSet resultSet = preSta.executeQuery();
        while(resultSet.next()){
            String string = resultSet.getString(1);
            System.out.println(string);
        }
        resultSet.close();
        connection.close();
    }
}
