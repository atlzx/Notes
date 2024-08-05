package expand;

import java.sql.*;

public class Test1 {
    public static void main(String[] args) throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        // 想要进行批量操作，需要传入 rewriteBatchedStatements=true 的配置
        // yyy对应的是密码，使用时修改为对应真实密码
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/atguigudb?rewriteBatchedStatements=true","root","yyy");
        test1(conn);


    }

    public static void test1(Connection con) throws SQLException {
        String sql="insert into jdbc_test2(name,age,sex) values (?,?,?)";  // 定义插入数据的SQL语句的格式
        PreparedStatement preSta = con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);  // 得到可以返回自增主键值的PreparedStatement对象
        long begin=System.currentTimeMillis();
        // 插入10000条数据
        for(int i=1;i<=10000;i++){
            preSta.setObject(1,"lzx"+i);
            preSta.setObject(2,i);
            preSta.setObject(3,i%2==0?"男":"女");
            preSta.addBatch();  // 将该操作装载
        }
        int[] ints = preSta.executeBatch();  // 调用executeBatch一次性执行全部的批量操作,不仅是insert，其它操作也能批量执行
        long end=System.currentTimeMillis();
        System.out.println("耗时:"+(int)(end-begin));
        System.out.println(ints.length);
        for(int i=0;i<ints.length;i++){
            System.out.println(ints[i]);  // 输出的是-2，即Statement.SUCCESS_NO_INFO,它表示执行成功了但是影响的行数无法统计
        }
        ResultSet generatedKeys = preSta.getGeneratedKeys();  // getGeneratedKeys可以得到得到当前自增的主键的集合，该集合仅一列，批量操作时可以得到多个主键值
        while(generatedKeys.next()){
            System.out.println(generatedKeys.getString(1));
        }
    }
}
