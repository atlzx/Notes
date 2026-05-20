package com.myProject.schedule.test;

import com.myProject.schedule.util.MD5Util;
import org.junit.Test;

public class TestMD5Util {
    @Test
    public void testEncrypt(){
        String result = MD5Util.encrypt("123456");
        System.out.println(result);
    }
}
