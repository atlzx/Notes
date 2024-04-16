package com.myProject.schedule.dao.impl;

import com.myProject.schedule.dao.BaseDao;
import com.myProject.schedule.dao.SysScheduleDao;
import com.myProject.schedule.pojo.SysSchedule;

import java.util.List;

public class SysScheduleDaoImple extends BaseDao implements SysScheduleDao {
    @Override
    public int addSchedule(SysSchedule schedule) {
        String sql="insert into sys_schedule values(DEFAULT,?,?,?)";
        System.out.println(sql);
        int row = baseUpdate(sql, schedule.getUid(), schedule.getTitle(), schedule.getCompleted());
        return row;
    }

    @Override
    public List<SysSchedule> findAll() {
        String sql="select sid,uid,title,completed from sys_schedule";
        List<SysSchedule> resultList = baseQuery(SysSchedule.class, sql);
        return resultList;
    }
}
