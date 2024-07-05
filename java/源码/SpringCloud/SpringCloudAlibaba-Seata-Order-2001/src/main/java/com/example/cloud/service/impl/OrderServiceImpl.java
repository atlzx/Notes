package com.example.cloud.service.impl;

import com.example.cloud.apis.AccountFeignApi;
import com.example.cloud.apis.StorageFeignApi;
import com.example.cloud.entities.Order;
import com.example.cloud.mapper.OrderMapper;
import com.example.cloud.service.OrderService;
import io.seata.core.context.RootContext;
import io.seata.spring.annotation.GlobalTransactional;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {
    @Resource
    private OrderMapper orderMapper;

    @Resource
    private AccountFeignApi accountFeignApi;

    @Resource
    private StorageFeignApi storageFeignApi;

    @Override
    // name属性用于给该事务起一个唯一的名称
    // rollbackFor表示事务执行时碰到什么异常执行回滚
    @GlobalTransactional(name = "seata-order-service-create-order",rollbackFor = Exception.class)
    public void create(Order order) {
        String xid = RootContext.getXID();
        // 新建订单
        log.info("开始新建订单");
        order.setStatus(0);  // 设置状态，为0为订单处理状态，转为1即为处理完的状态
        int result = orderMapper.insertSelective(order);
        if(result>0){
            Order orderFromDB = orderMapper.selectOne(order);  // 得到新插入的订单对象
            log.info("新建订单对象:{}",orderFromDB);
            // 开始扣减库存
            log.info("开始扣减库存");
            storageFeignApi.decrease(orderFromDB.getProductId(), orderFromDB.getCount());
            log.info("扣减库存完成");
            // 扣减用户余额
            log.info("开始扣减用户余额");
            accountFeignApi.decrease(orderFromDB.getUserId(), orderFromDB.getMoney());
            log.info("扣减余额完成");
            // 修改订单状态
            log.info("开始修改订单状态");
            Example example=new Example(Order.class);
            Example.Criteria criteria= example.createCriteria();
            criteria
                .andEqualTo("userId",orderFromDB.getUserId())
                .andEqualTo("status",0);
            int res = orderMapper.updateByExampleSelective(orderFromDB, example);
            if(res>0){
                log.info("修改成功");
            }
        }
        log.info("结束创建订单");
    }
}
