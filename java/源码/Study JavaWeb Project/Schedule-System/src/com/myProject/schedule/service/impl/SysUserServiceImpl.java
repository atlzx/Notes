package com.myProject.schedule.service.impl;

import com.myProject.schedule.dao.SysUserDao;
import com.myProject.schedule.dao.impl.SysUserDaoImpl;
import com.myProject.schedule.pojo.SysUser;
import com.myProject.schedule.service.SysUserService;
import com.myProject.schedule.util.MD5Util;

import java.util.List;

public class SysUserServiceImpl implements SysUserService {

    private SysUserDaoImpl sysUserDaoImpl=new SysUserDaoImpl();
    public int regist(SysUser user){
        String userPwd = user.getUserPwd();
        String encrypt = MD5Util.encrypt(userPwd);
        user.setUserPwd(encrypt);
        return sysUserDaoImpl.addSysUser(user);
    }

    public SysUser findByUserName(String userName){
        return sysUserDaoImpl.findByUserName(userName);  // 该操作是通过数据库进行的，应该使用DAO类中的方法，调用方法后仅需返回即可
    }
}
