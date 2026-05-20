package com.springboot.example.mybatisplus;

import com.springboot.example.mybatisplus.SpringBootMybatisPlusApplication;
import com.springboot.example.mybatisplus.entity.User;
import com.springboot.example.mybatisplus.mapper.UserMapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

@SpringBootTest
@Slf4j
public class MapperTest {
    @Resource
    private UserMapper userMapper;
    @Test
    public void test1(){
        // selectList方法用来查询满足条件的行，并以List的形式返回。传入null即代表得到表中的所有行
        List<User> users = userMapper.selectList(null);
        users.stream().forEach(
            (item)->{
                log.info(item.toString());
            }
        );
    }

    @Test
    public void insertTest(){
        User user=new User();
        user.setAge(18);
        user.setName("ZhangSan");
        user.setEmail("ZhangSan@qq.com");
        // insert方法可以接收一个不带id的实体类对象，如果实体类对象没有id，那么Mybatis-plus会使用雪花算法生成一个id(很长，需要用Long接收)
        int result = userMapper.insert(user);
        log.info(String.valueOf(result));
    }

    @Test
    public void deleteTest(){
        // 删除方法有三个，通过id删除、依据map条件删除、批量删除
        // deleteById，就是根据id删除
        int res1 = userMapper.deleteById(1800869306294407170L);
        log.info(String.valueOf(res1));
        // 依据map删除，相当于 xxx where aaa = bbb and ccc = ddd ...
        // map的key需要和数据库表中的字段一致
        Map<String,Object> map=new HashMap<>();
        map.put("name","张三");
        map.put("age",23);
        int res2 = userMapper.deleteByMap(map);
        log.info(String.valueOf(res2));
        // 使用Collection进行删除，即 xxx where id in (1,2,3,4,...) ...
        List<Long> list=new ArrayList<>(Arrays.asList(1L,2L,3L));
        int res3 = userMapper.deleteByIds(list);
        log.info(String.valueOf(res3));
    }

    @Test
    public void updateTest(){
        User user=new User();
        user.setId(4L);
        user.setName("李四");
        user.setEmail("lisi@qq.com");
        // Mybatis-plus会根据传入实体类的id进行修改，如果实体类中没有设置对应字段的名称，那么就不会修改对应字段
        int res = userMapper.updateById(user);
        log.info(String.valueOf(res));
    }

    @Test
    public void selectTest(){
        // 根据id进行查询
        User user = userMapper.selectById(1L);
        log.info(user.toString());

        Map<String,Object> map=new HashMap<>();
        map.put("name","Jack");
        map.put("age",20);
        // 根据Map指定的条件进行查询
        List<User> users = userMapper.selectByMap(map);
        log.info(users.toString());
        // 根据条件选择器进行查询，传入null表示得到所有数据
        List<User> result = userMapper.selectList(null);
        log.info(result.toString());

        // 使用自定义接口方法（即Mybatis原生方法）进行查询
        Map<String, Object> resMap = userMapper.selectMapById(1L);
        log.info(resMap.toString());


    }
}
