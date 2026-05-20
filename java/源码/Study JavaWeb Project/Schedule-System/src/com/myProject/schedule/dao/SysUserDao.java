package com.myProject.schedule.dao;

import com.myProject.schedule.pojo.SysUser;

public interface SysUserDao {

    /**
     * 方法用来尝试向数据库中插入数据
     * @param user 参数接收一个SysUser对象来将其中的数据插入数据库
     * @return 返回插入结果，大于0的值表示插入成功，等于0的值表示插入失败
     */
    int addSysUser(SysUser user);


    /**
     * 方法用来得到通过用户名查询到的SysUser对象
     * @param userName 参数用来接收客户端传入的用户名
     * @return 返回查询到的SysUser对象，如果没有查询到返回为null
     */
    SysUser findByUserName(String userName);
}
