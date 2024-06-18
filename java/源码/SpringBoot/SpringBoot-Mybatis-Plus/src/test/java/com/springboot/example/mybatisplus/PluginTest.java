package com.springboot.example.mybatisplus;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.springboot.example.mybatisplus.entity.Product;
import com.springboot.example.mybatisplus.entity.User;
import com.springboot.example.mybatisplus.mapper.ProductMapper;
import com.springboot.example.mybatisplus.mapper.UserMapper;
import com.springboot.example.mybatisplus.utils.SpringUtil;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class PluginTest {
    @Resource
    private UserMapper userMapper;

    @Resource
    private ProductMapper productMapper;
    @Test
    public void pagePluginTest(){
        Page<User> userPage = userMapper.selectPage(new Page<>(2, 3), null);
        log.info(userPage.getRecords().toString());
        log.info(userPage.getRecords().toString());
        log.info(String.valueOf(userPage.getPages()));
        log.info(String.valueOf(userPage.getCurrent()));
        log.info(String.valueOf(userPage.getSize()));
        log.info(String.valueOf(userPage.hasNext()));
        log.info(String.valueOf(userPage.hasPrevious()));

    }

    @Test
    public void optimisticBlockInterceptorTest(){
        // 小李跟小王一起修改对应的数据库表中的同一行数据，他们都先读取了数据库中的行数据，小李先修改，小王后修改
        // 会发生脏读问题（后读取到行数据信息的人在先读取到行信息的人未提交数据之前就读取到了行信息）
        Product liProduct = productMapper.selectById(1L);
        log.info("小李读取到的数据价格:{}",liProduct.getPrice());
        Product wangProduct = productMapper.selectById(1L);
        log.info("小王读取到的数据价格:{}",wangProduct.getPrice());
        liProduct.setPrice(liProduct.getPrice()+50);  // 小李加了50块钱
        productMapper.updateById(liProduct);
        log.info("小李修改后的价格:{}",liProduct.getPrice());
        wangProduct.setPrice(wangProduct.getPrice()-30);  // 小王减了30块钱
        int res = productMapper.updateById(wangProduct);
        log.info("小王修改后的价格:{}",wangProduct.getPrice());
        if(res==0){
            // 小王执行失败，再执行一次
            wangProduct = productMapper.selectById(1L);
            wangProduct.setPrice(wangProduct.getPrice()-30);
            productMapper.updateById(wangProduct);
        }
        Product bossProduct = productMapper.selectById(1L);
        log.info("老板读取到的数据价格:{}",bossProduct.getPrice());  // 如果上了乐观锁，老板读取不到小王修改的数据，因为小王的版本号不正确

    }




}
