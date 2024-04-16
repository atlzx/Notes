package com.myProject.schedule.dao.impl;

import com.myProject.schedule.dao.BaseDao;
import com.myProject.schedule.dao.SysUserDao;
import com.myProject.schedule.pojo.SysUser;

import java.util.List;

public class SysUserDaoImpl extends BaseDao implements SysUserDao {
    public int addSysUser(SysUser user){
        // 定义插入语句
        String sql="insert into sys_user values(DEFAULT,?,?)";
        // 调用baseUpdate方法尝试进行插入，并返回插入结果
        return baseUpdate(sql,user.getUserName(),user.getUserPwd());
    }

    public SysUser findByUserName(String userName) {
        // 定义插入语句
        String sql="select uid,username as userName,user_pwd as userPwd from sys_user where username=?";
        // 调用baseQuery方法，根据userName进行查询，并返回查询结果
        // baseQuery方法无论是否查找到结果都会返回list列表，如果未查询到，返回的是一个空列表
        List<SysUser> queryResult = baseQuery(SysUser.class, sql, userName);
        if(!queryResult.isEmpty()){
            return queryResult.get(0);
        }else{
            return null;  // 如果queryResult是空列表，那么向上返回null
        }
    }
}
