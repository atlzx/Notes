package utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BaseDao {
    public int executeUpdate(String sql,Object ...params) throws SQLException {
        Connection con = JDBCUtils.getConnection();
        PreparedStatement preSta = con.prepareStatement(sql);
        if(params!=null&&params.length>0){
            for(int i=1;i<= params.length;i++){
                preSta.setObject(i,params[i-1]);
            }
        }
        int rows=preSta.executeUpdate();
        preSta.close();
        return rows;
    }

    // 使用泛型和反射，来灵活的接收各个类的查询情况
    public <T> List<T> executeQuery(Class<T> clazz, String sql, Object ...params) throws SQLException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Connection con = JDBCUtils.getConnection();
        PreparedStatement preSta = con.prepareStatement(sql);
        List<T> list=new ArrayList<>();
        if(params!=null&&params.length>0){
            for (int i = 1; i <= params.length; i++) {
                preSta.setObject(i,params[i-1]);
            }
        }

        ResultSet rs = preSta.executeQuery();
        while(rs.next()){
            Constructor<T> constructor = clazz.getDeclaredConstructor();  // 得到对应类对象的声明的空参构造器
            T instance = constructor.newInstance();  // 创建一个空参构造器实例
            Field[] fields = clazz.getDeclaredFields();  // 得到对应类对象的声明的属性数组
            for (int i = 0; i < fields.length; i++) {
                fields[i].setAccessible(true);  // 设置属性可修改，打破private修饰符的限制
                fields[i].set(instance,rs.getObject(i+1));  // 给对应实例对象的对应属性赋值
            }
            list.add(instance);  // 将结果添加进列表内
        }
        // 释放资源
        rs.close();
        preSta.close();
        return list;

    }
}
