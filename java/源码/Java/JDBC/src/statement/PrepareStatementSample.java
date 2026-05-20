package statement;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class PrepareStatementSample {
    public static void main(String[] args) throws Exception{
        Class.forName("com.mysql.cj.jdbc.Driver");
        Scanner sc=new Scanner(System.in);
        // yyy对应的是密码，使用时修改为对应真实密码
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/atguigudb", "root", "yyy");
        test1(sc,con);
        test2(sc,con);
        test3(sc,con);
        test4(sc,con);
        con.close();
    }

    // 查询的测试函数
    public static void test1(Scanner sc,Connection con) throws Exception{
        String selectSql="select user_name,password from jdbc_test1 where user_name = ? and password = ?";
        PreparedStatement preSta = con.prepareStatement(selectSql);
        System.out.print("请输入想查询的用户名:");
        String userName=sc.nextLine();
        System.out.print("请输入密码:");
        String password=sc.nextLine();
        preSta.setObject(1,userName);  // 调用setObject方法来向占位符设置值
        preSta.setObject(2,password);
        ResultSet rs = preSta.executeQuery();  // 调用executeQuery方法来执行查询语句，preparedStatement由于已经知道了sql格式，因此不需要参数
        ResultSetMetaData metaData = rs.getMetaData();  // getMetaData方法来得到游标指向的行的各列的值的集合
        ArrayList<Map<String,String>> list=new ArrayList<>();
        int length=metaData.getColumnCount();  // getColumnCount方法来得到列的数量，也就是该行有多少个字段值
        if(rs.next()){
            Map<String,String> map=new HashMap<>();
            // 下面的方法用来方便的自动化遍历各行的各个数据
            for(int i=1;i<=length;i++){
                String value = rs.getString(i);
                String key=metaData.getColumnLabel(i);  // getColumnLabel方法用来得到列的名称，优先获取别名，没有别名再取列名
                map.put(key,value);
            }
            list.add(map);
            for (int i = 0; i < list.size(); i++) {
                Map<String,String> newMap=list.get(i);
                for(var entry:newMap.entrySet()){
                    System.out.println("key="+entry.getKey()+" -> value="+entry.getValue());
                }
            }
        }else{
            System.out.println("查无此人");
        }

    }

    // 插入的测试函数
    public static void test2(Scanner sc,Connection con) throws Exception{
        String insertSql="insert into jdbc_test1 values(?,?)";
        PreparedStatement preSta = con.prepareStatement(insertSql);
        System.out.print("请输入用户名:");
        String userName=sc.nextLine();
        System.out.print("请输入密码:");
        String password=sc.nextLine();
        preSta.setObject(1,userName);
        preSta.setObject(2,password);
        int rows = preSta.executeUpdate();
        if(rows>0){
            System.out.println("插入成功");
        }else{
            System.out.println("插入失败");
        }
    }

    // 更新的测试函数
    public static void test3(Scanner sc,Connection con) throws Exception{
        String updateSql="update jdbc_test1 set user_name = ? where user_name = ?";
        PreparedStatement preSta = con.prepareStatement(updateSql);
        con.rollback();
        System.out.print("请输入修改后的用户名:");
        String updatedUserName=sc.nextLine();
        System.out.print("请输入修改前的用户名:");
        String prevUserName=sc.nextLine();
        preSta.setObject(1,updatedUserName);
        preSta.setObject(2,prevUserName);
        int rows = preSta.executeUpdate();
        if(rows>0){
            System.out.println("更新成功");
        }else{
            System.out.println("更新失败");
        }
    }

    // 删除的测试函数
    public static void test4(Scanner sc,Connection con) throws Exception{
        String deleteSql="delete from jdbc_test1 where user_name = ?";
        PreparedStatement preSta = con.prepareStatement(deleteSql);
        System.out.print("请输入想删除的用户名:");
        String userName=sc.nextLine();
        preSta.setObject(1,userName);
        int rows = preSta.executeUpdate();
        if(rows>0){
            System.out.println("删除成功");
        }else{
            System.out.println("删除失败");
        }

    }

}
