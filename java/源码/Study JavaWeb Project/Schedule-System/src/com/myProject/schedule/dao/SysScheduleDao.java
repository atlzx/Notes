package com.myProject.schedule.dao;

import com.myProject.schedule.pojo.SysSchedule;
import com.myProject.schedule.pojo.SysUser;

import java.util.List;


public interface SysScheduleDao {
    /**
     * 该方法用于向数据库添加一条日程
     * @param schedule 接收一个schedule对象，
     * @return 返回值大于0表示添加成功，返回值等于0表示添加失败
     */
    int addSchedule(SysSchedule schedule);

    /* findAll方法用于查询当前所有日程
    *  该方法返回一个集合
    *  */
    List<SysSchedule> findAll();
}
