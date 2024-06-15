package com.springboot.example.mybatisplus;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.springboot.example.mybatisplus.entity.User;
import com.springboot.example.mybatisplus.mapper.UserMapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
@Slf4j
public class WrapperTest {
    @Resource
    private UserMapper userMapper;
    @Test
    public void queryWrapperTest1(){
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        QueryWrapper<User> wrapper = queryWrapper
            .like("name", "a")
            .between("age", 20, 30)
            .isNotNull("email");
        List<User> users = userMapper.selectList(wrapper);
        users.stream().forEach(
            (user)->{
                log.info(user.toString());
            }
        );
    }

    @Test
    public void updateWrapperTest(){
        // UpdateWrapper相较于QueryWrapper，它在配置条件的基础上，还可以配置要修改的字段和对应值
        UpdateWrapper<User> updateWrapper =new UpdateWrapper<>();
        UpdateWrapper<User> wrapper = updateWrapper
            .ge("age", 10)  // 年龄需要大于等于10
            .le("age", 20)  // 年龄需要小于等于20
            .isNull("email")  // email需要为空
            .set("name","小明")  // 使用set方法来直接设置想修改的字段和值
            .set("email","ming@163.com");
        int res = userMapper.update(wrapper);
        log.info(String.valueOf(res));
    }

    @Test
    public void queryWrapperTest2(){
        QueryWrapper<User> queryWrapper=new QueryWrapper<User>()
            .ge("age",20)
            // 向and和or方法传入lambda表达式，它会自动将lambda表达式内的条件在生成sql语句的时候加上小括号，以获得更高的优先级
            .and(
                (i)->{
                    i
                        .like("name","a")
                        .likeRight("name","e");

                }
            )
            .or()
            .isNotNull("email");
        List<User> users = userMapper.selectList(queryWrapper);
        users.stream().forEach(
            item->log.info(item.toString())
        );
    }

    @Test
    public void lambdaQueryWrapperTest(){
        // LambdaWrapper与传统Wrapper的区别就是它们的方法需要一个判断条件的参数来决定该条件是否加入到生成的SQL语句中
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<User>()
            .like(User::getName,"a")
            .select(User::getAge,User::getId);
        List<User> users = userMapper.selectList(lambdaQueryWrapper);
        users.stream().forEach(
            (item)->{
                log.info(item.toString());
            }
        );
    }

    @Test
    public void inSqlTest(){
        // 子查询样例
        QueryWrapper<User> queryWrapper=new QueryWrapper<User>()
            .like("name","S")
            .inSql("id","select id from user where age >= 20");
        userMapper.selectList(queryWrapper).stream().forEach(
            (item)->{
                log.info(item.toString());
            }
        );

    }
}
