package druid;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import org.junit.Test;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class DruidSample {
    @Test
    public void test1() throws SQLException {
        // 下面是硬编码实现连接池的方式
        String sql="select * from jdbc_test1";
        DruidDataSource dataSource=new DruidDataSource();  // 创建连接池对象
        // 下面四个设置是必须的
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");  // 告知驱动全类名路径
        dataSource.setUrl("jdbc:mysql://localhost:3306/atguigudb");  // 告知URL
        dataSource.setUsername("root");  // 告知连接用户名
        dataSource.setPassword("yyy");  // 告知密码

        // 下面的设置可选

        dataSource.setInitialSize(5);  // 设置初始化连接数量
        dataSource.setMaxActive(10);  // 设置最大连接数量

        Connection con = dataSource.getConnection();  // 得到连接，该连接对象是一个DruidPooledConnection对象，它是Connection接口的实现类
        // 尝试执行sql语句
        PreparedStatement preSta = con.prepareStatement(sql);
        ResultSet rs = preSta.executeQuery();
        while(rs.next()){
            System.out.println(rs.getString(1)+" "+rs.getString(2));
        }

        con.close();   // 调用连接池得到的连接对象的close方法，不会释放资源，而是将连接回收到回收池中
    }

    @Test
    public void test2() throws Exception {
        String sql="select * from jdbc_test1";

        // 通过类对象getResourceAsStream方法，可以得到相对于类所在目录下指定文件的输入流对象
        InputStream input = DruidSample.class.getResourceAsStream("info.properties");
        Properties properties=new Properties();
        properties.load(input);
        DataSource dataSource = DruidDataSourceFactory.createDataSource(properties);
        Connection con = dataSource.getConnection();
        PreparedStatement preSta = con.prepareStatement(sql);
        ResultSet rs = preSta.executeQuery();
        while(rs.next()){
            System.out.println(rs.getString(1)+" "+rs.getString(2));
        }

        con.close();   // 调用连接池得到的连接对象的close方法，不会释放资源，而是将连接回收到回收池中
    }

}
