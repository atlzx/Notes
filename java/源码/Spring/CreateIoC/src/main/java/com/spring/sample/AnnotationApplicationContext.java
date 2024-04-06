package com.spring.sample;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class AnnotationApplicationContext implements ApplicationContext {
    private Map<Class<?>, Object> map = new HashMap<>();

    public AnnotationApplicationContext() {
    }

    public AnnotationApplicationContext(String url) {
        try {
            // replaceAll的第二个参数不可以是字符 '\\'
            url = url.replaceAll("\\.", "\\\\");
            // 通过当前线程的类加载器对象得到当前绝对路径
            // 并不是一定要用线程来得到类加载器对象，只要得到的类加载器对象属于应用程序加载器对象即可
            URL resource = Thread.currentThread().getContextClassLoader().getResource(url);
            // 将字符串格式转为UTF-8格式
            String filePath = URLDecoder.decode(resource.getFile(), "utf8");
            initBean(new File(filePath), filePath.substring(0, filePath.length() - url.length()));
            dependencyInject();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("初始化容器过程中出现错误");
        }
    }

    @Override
    public <T> T getBean(Class<T> clazz) {
        return (T) map.get(clazz);
    }


    private void initBean(File file, String filePath) throws Exception {
        // 判断是否是文件夹
        if (file.isDirectory()) {
            // 如果是文件夹，那么进行递归得到全部的文件
            File[] files = file.listFiles();
            // 遍历检查所有的子文件
            for (File i : files) {
                initBean(i, filePath);
            }
        } else {
            // 如果不是文件夹，那么就是文件，此时需要判断文件是否是.class文件

            String name = file.getName();
            // 判断文件是否是.class文件
            if (name.endsWith(".class")) {
                // 如果是.class文件，处理路径，得到类的全类名
                String classPath = file.getAbsolutePath().substring(filePath.length() - 1).replaceAll("\\\\", ".").replace(".class", "");
                // 生成一个对象
                Class<?> clazz = Class.forName(classPath);
                clazz.is
                // 查看对象是否是接口
                if (!clazz.isInterface()) {
                    // 如果不是接口，那么得到对象关于bean的注解对象，如果有，那么说明存在注解
                    Bean bean = clazz.getDeclaredAnnotation(Bean.class);
                    if (bean != null) {
                        // 如果bean不是null，说明存在Bean注解，那么生成该类的实例对象
                        Object instance = clazz.getDeclaredConstructor().newInstance();
                        // 查看该类是否实现了接口，如果有，那么将该类的接口加入map中
                        if (clazz.getInterfaces().length > 0) {
                            map.put(clazz.getInterfaces()[0], instance);
                        }
                        // 同时，也将该类的class加入。该情况同样适配于未实现接口的类对象
                        map.put(clazz, instance);
                    }
                }
            }
        }
    }

    private void dependencyInject() throws Exception{
        // 遍历map
        for(Map.Entry<Class<?>,Object> entry: map.entrySet()){
            // 得到刚刚初始化完成的bean对象
            Object bean=entry.getValue();
            // 得到每一个属性
            Field[] fields = entry.getValue().getClass().getDeclaredFields();
            // 遍历属性，查看哪个被DependencyInject注解标注
            for(Field i:fields){
                if(i.getAnnotation(DependencyInject.class)!=null){
                    // 打破private修饰符限制
                    i.setAccessible(true);
                    // 设置该属性的值
                    i.set(bean,map.get(i.getType()));
                }
            }
        }
    }
}
