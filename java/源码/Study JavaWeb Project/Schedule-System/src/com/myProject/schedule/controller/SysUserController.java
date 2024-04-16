package com.myProject.schedule.controller;

import com.myProject.schedule.pojo.SysUser;
import com.myProject.schedule.service.impl.SysUserServiceImpl;
import com.myProject.schedule.util.MD5Util;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

/* 通过继承BaseController类的方式来实现对service方法的实现，同时减少代码冗余 */
@WebServlet("/user/*")
public class SysUserController extends BaseController{

    private SysUserServiceImpl sysUserService=new SysUserServiceImpl();
    protected void regist(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String loginName = req.getParameter("login_name");
        String pwd = req.getParameter("pwd");
        int row = sysUserService.regist(new SysUser(null, loginName, pwd));
        if (row > 0) {
            resp.sendRedirect("/registSuccess.html");
        }else{
            resp.sendRedirect("/registFail.html");
        }
    }


    protected void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String loginName = req.getParameter("login_name");
        String pwd = req.getParameter("pwd");
        SysUser user = sysUserService.findByUserName(loginName);
        if(user==null){
            resp.sendRedirect("/loginUserNameError.html");
        }else if( ! MD5Util.encrypt(pwd).equals(user.getUserPwd())){
            resp.sendRedirect("/loginUserPwdError.html");
        }else{
            resp.sendRedirect("/showSchedule.html");
        }
    }
}
