package com.myProject.schedule.test;

import com.myProject.schedule.dao.BaseDao;
import com.myProject.schedule.pojo.SysUser;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

public class TestBaseDao {
    private static BaseDao basedao;

    @BeforeClass
    /* 使用BeforeClass注解，方法必须是静态方法，因此要用static关键字 */
    public static void newBaseDao(){
        basedao=new BaseDao();
    }

    @Test
    public void testBaseQueryObject(){
        String sql="select count(1) from sys_user";
        Long count = basedao.baseQueryObject(long.class, sql);
        System.out.println(count);
    }

    @Test
    public void testBaseQuery(){

        String sql = "select uid,username as userName,user_pwd as userPwd from sys_user";
        /* 该结果集返回的数据必须与SysUser类中所定义的属性名称对应，不然会报错 */
        List<SysUser> sysUserList = basedao.baseQuery(SysUser.class, sql);
        sysUserList.forEach(System.out::println);
    }

    @Test
    public void testBaseUpdate(){
        String sql="insert into sys_schedule values(DEFAULT,?,?,?)";
        int result = basedao.baseUpdate(sql, 1, "学习JAVA", 1);
        System.out.println(result);
    }
}
