package com.myProject.schedule.service;

import com.myProject.schedule.pojo.SysUser;

import java.util.List;

/* 该接口用来定义与sys_user相关的service方法 */
public interface SysUserService {

    /**
     * regist方法传入可以进行注册，它会对参数进行数据处理后尝试向数据库中添加信息，如果成功返回一个大于0的值，不成功返回0
     * @param user 参数是一个SysUser类型的变量，它用来被regist方法使用来读取用户传入的数据，并对其进行数据处理
     * @return 方法返回值大于0表明修改成功，方法返回0表示修改失败，如果返回成功，返回的正数表示向数据库中添加的行数
     */
    int regist(SysUser user);


    /**
     * 方法用来得到通过用户名查询到的SysUser对象
     * @param userName 参数用来接收客户端传入的用户名
     * @return 返回查询到的SysUser对象，如果没有查询到返回为null
     */
    SysUser findByUserName(String userName);
}
