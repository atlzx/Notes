package com.springboot.example.springboottest;


import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.context.event.ApplicationStartingEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Slf4j
@Component
public class BeanUtil implements ApplicationListener<ContextRefreshedEvent> {
    @Getter
    private static ApplicationContext ioc=null;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        log.info("BeanUtil获取IOC对象");
        ioc=event.getApplicationContext();
    }

    public static <T> T getBean(Class<T> clazz){
        log.info("{}", clazz.getName());
        T res;
        try{
            res= ioc.getBean(clazz);
        }catch (Exception e){
            // 进入此代码块时，意味着ioc通过Class对象得到指定对象可能出现了问题
            // 出现的问题可能包括
            //    找不到
            //    有多个满足条件的bean，出现该问题时，下面的代码会尝试继续使用传入的类名和类名的小驼峰命名法继续尝试得到对应的对象
            //    出现异常
            // 下面是得到类名的小驼峰命名法字符串，首先通过getName得到类名的全路径并使用.隔开
            String[] split = clazz.getName().split("\\.");
            // 得到最后一项，即类名
            String className=split[split.length-1];
            // 将第一个字符替换为小驼峰字符
            className=className.replace(className.charAt(0),(char)(className.charAt(0)+32));
            try{
                // 尝试继续使用传入的类名和类名的小驼峰命名法继续尝试得到对应的对象
                res = ioc.getBean(className, clazz);
            }catch(Exception ex){
                // 如果再出现异常，那么抛出
                throw ex;
            }
            return res;
        }
        return res;
    }

    public static <T> Map<String,T> getBeans(Class<T> clazz){
        return ioc.getBeansOfType(clazz);
    }

    /**
     * 忽略大小写的复制属性
     * @param source 源对象
     * @param clazz 想生成的目的类的Class对象
     * @return 赋值好的新的目的对象
     * @param <T> 目的对象
     */
    public static <T> T copyPropertiesIgnoreCase(Object source,Class<T> clazz){
        try{
            Field[] sourceFields = source.getClass().getDeclaredFields();
            log.info(Arrays.toString(sourceFields));
            Map<String,Field> targetFieldMap = Arrays.stream(clazz.getDeclaredFields()).collect(
                Collectors.toMap(
                    item->item.getName().toLowerCase(),
                    item->item,
                    (o1,o2)->o1
                )
            );
            log.info(targetFieldMap.toString());
            T instance = clazz.newInstance();
            for(Field sourceField : sourceFields){
                Field field = targetFieldMap.get(sourceField.getName().toLowerCase());
                if(field != null){
                    field.setAccessible(true);
                    sourceField.setAccessible(true);
                    field.set(instance,sourceField.get(source));
                }
            }
            return instance;
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }


    /**
     * 把源对象的属性赋值给目的对象的对应属性并返回生成好的目的对象，但是属性名不区分大小写
     * @param source 源对象
     * @param clazz 想生成的目的类的Class对象
     * @return 赋值好的新的目的对象
     * @param <T> 目的对象
     */
    public static <T> T copyProperties(Object source,Class<T> clazz){
        try{
            Field[] sourceFields = source.getClass().getDeclaredFields();
            log.info(Arrays.toString(sourceFields));
            Map<String,Field> targetFieldMap = Arrays.stream(clazz.getDeclaredFields()).collect(
                Collectors.toMap(
                    item->item.getName().toLowerCase(),
                    item->item,
                    (o1,o2)->o1
                )
            );
            log.info(targetFieldMap.toString());
            T instance = clazz.newInstance();
            for(Field sourceField : sourceFields){
                Field field = targetFieldMap.get(sourceField.getName().toLowerCase());
                if(field != null){
                    field.setAccessible(true);
                    sourceField.setAccessible(true);
                    field.set(instance,sourceField.get(source));
                }
            }
//            T instance = clazz.getDeclaredConstructor().newInstance();
//            BeanUtils.copyProperties(instance,source);
//            return instance;
            return instance;
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
