package com.springboot.example.springbootwebsocket.utils;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

@Slf4j
public class CommonUtil {
    public static String getClassName(Class<?> clazz,Boolean ...firstToCase){
        String className = clazz.getSimpleName();
        String sub1=null;
        sub1 = className.substring(0, 1);
        if(firstToCase == null || firstToCase[0]){
            sub1=sub1.toLowerCase();
        }
        String sub2 = className.substring(1);
        log.info(sub1 + sub2);
        return sub1+sub2;
    }

    public static <T> T getInstance(Map<?,?> map, Class<T> clazz) throws InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        Constructor<T> constructor = clazz.getDeclaredConstructor();
        Field[] fields = clazz.getDeclaredFields();
        String fieldClass,mapValueClass;
        constructor.setAccessible(true);
        T instance = constructor.newInstance();
        for(int i=0;i< fields.length;i++){
            fields[i].setAccessible(true);
            fieldClass=fields[i].getType().getSimpleName();
            mapValueClass=map.get(fields[i].getName()).getClass().getSimpleName();

            boolean isMapping = fieldClass.equals(mapValueClass);
            if(isMapping){
                fields[i].set(instance,map.get(fields[i].getName()));
            }else{
                Method valueOf = fields[i].getType().getDeclaredMethod("valueOf", map.get(fields[i].getName()).getClass());
                Object invoke = valueOf.invoke(null, map.get(fields[i].getName()));
                fields[i].set(instance,invoke);
            }

        }
        return instance;
    }
}
