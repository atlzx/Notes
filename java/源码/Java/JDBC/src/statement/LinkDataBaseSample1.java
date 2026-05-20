package statement;


// MySQL5+ 使用com.mysql.jdbc
// MySQL8+ 使用com.mysql.cj.jdbc

import java.sql.*;
import java.util.Properties;
import java.util.Scanner;


public class LinkDataBaseSample1 {
    public static void main(String[] args) throws SQLException {
        Scanner sc = new Scanner(System.in);
        /*
             连接数据库需要下面的步骤:
             1. 数据库的ip地址
             2. 数据库端口号
             3. 账号
             4. 密码
             5. 连接数据库的名称
            */
        String sql = "select * from jdbc_test1";  // 声明查询语句
        // 下面的注册驱动会导致驱动被注册两次，因此不推荐使用
//        DriverManager.registerDriver(new Driver());
        // 下面是使用反射的方式加载类，从而注册数据库驱动
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        // 创建Properties对象
        Properties pro = new Properties();
        pro.put("user", "root");
        pro.put("password", "yyy");  //yyy对应的是密码，使用时修改为对应真实密码

        System.out.println("请输入用户名:");
        String userName = sc.nextLine();
        System.out.println("请输入密码:");
        String pwd = sc.nextLine();

        try (
            /*
            使用getConnection静态方法来建立连接
                url参数格式:
                    jdbc:{mysql\|oracle}://{ip地址\|主机名}:端口号/数据库名[?key1=value1&key2=value2&....(可选信息)]
                    如:jdbc:mysql://localhost:3306/atguigudb，端口号为3306且是本地主机时可以简写为jdbc:mysql:///atguigudb
                user参数:用来表示数据库用户名
                password:用来表示密码
            */

            // 下面演示三个得到数据库连接对象的方式，都是调用一样的方法，只是传入的参数不一样
            // yyy对应的是密码，使用时修改为对应真实密码
            Connection con1 = DriverManager.getConnection("jdbc:mysql:///atguigudb", "root", "yyy");
            Connection con2 = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/atguigudb", pro);
            Connection con3 = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/atguigudb?user=root&password=yyy");
            Statement statement = con1.createStatement();  // 创建Statement实例对象
            // 使用statement对象进行执行可能会出现SQL注入的风险，如在password输入: ' or '1'='1 也会登陆成功
            ResultSet rs = statement.executeQuery(sql + " where user_name='" + userName + "' and password='" + pwd + "';");
        ) {
            // executeQuery方法专门用来执行查询语句，并将查询结果整合为ResultSet对象返回

            // ResultSet对象有一个专门的游标，它默认指向第一行数据的上面，next()方法可以使其下移一行，如果成功下移，方法会返回true
            // 一般使用while循环搭配next方法来实现结果集的遍历，这里只需要让游标下移一位判断是否有结果就可以了，因为有结果说明一定存在该用户，那么肯定是登陆成功的
//            System.out.println(sql + " where user_name='" + userName + "' and password='" + pwd + "';");
            if (rs.next()) {
                // getXxx方法可以传入次序或字段名来得到当前游标所指向行的相关数据
                System.out.println(rs.getString(1));
                System.out.println(rs.getString("password"));
                System.out.println("登陆成功");
            } else {
                System.out.println("登陆失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
