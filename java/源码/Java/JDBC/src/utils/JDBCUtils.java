package utils;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class JDBCUtils {
    /*
        JDBCUtils有两个属性:dataSource和threadLocal
            dataSource是数据库的连接池对象
            threadLocal是数据库的本地线程对象，它可以承载一个线程的不同方法都能共享的变量
     */
    private static DataSource dataSource;
    private static ThreadLocal<Connection> threadLocal;

    static {
        // 使用软编码实现线程池初始化
        Properties properties = new Properties();
        InputStream input = JDBCUtils.class.getResourceAsStream("info.properties");
        try {
            properties.load(input);
            dataSource = DruidDataSourceFactory.createDataSource(properties);
            threadLocal = new ThreadLocal<>();
        } catch (Exception e) {
            throw new RuntimeException("初始化JDBCUtils工具类属性出错");
        }
    }

    public static Connection getConnection() throws SQLException {

        Connection con=threadLocal.get();

        if (con == null) {
            con=dataSource.getConnection();
            threadLocal.set(con);
        }
        return con;
    }

    public static void releaseConnection() throws SQLException{
        Connection con = threadLocal.get();
        // 判断连接是否为true且通过判断是否为自动提交来判断是否在执行事务
        if(con!=null){
            con.setAutoCommit(true);  // 重新设置自动提交
            con.close();  // 回收连接
            threadLocal.remove();  // 把Connection对象从threadLocal中移除，表示事务结束
        }
    }
}
