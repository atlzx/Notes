package com.myProject.schedule.test;

import com.myProject.schedule.dao.SysScheduleDao;
import com.myProject.schedule.dao.impl.SysScheduleDaoImple;
import com.myProject.schedule.pojo.SysSchedule;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

public class TestSysScheduleDao {
    private static SysScheduleDaoImple sysScheduleDaoImple;

    @BeforeClass
    public static void init() {
        sysScheduleDaoImple=new SysScheduleDaoImple();
    }

    @Test
    public void testAddSchedule(){
        int result = sysScheduleDaoImple.addSchedule(new SysSchedule(null, 2, "摆烂", 1));
        System.out.println(result);
    }

    @Test
    public void testFindAll(){
        List<SysSchedule> result = sysScheduleDaoImple.findAll();
        result.forEach(System.out::println);
    }
}
