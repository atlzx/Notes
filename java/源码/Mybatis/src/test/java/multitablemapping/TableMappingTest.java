package multitablemapping;

import com.mybatis.example.multitablemapping.mapper.CustomerMapper;
import com.mybatis.example.multitablemapping.mapper.OrderMapper;
import com.mybatis.example.multitablemapping.pojo.Customer;
import com.mybatis.example.multitablemapping.pojo.Order;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;

public class TableMappingTest {
    private SqlSession session;
    @BeforeEach
    // @BeforeEach注解使作用方法在每个方法执行前调用
    public void before() throws IOException {
        InputStream in = Resources.getResourceAsStream("multitablemapping/mybatis-config.xml");
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(in);
        session = factory.openSession();
    }

    @AfterEach
    // @BeforeEach注解使作用方法在每个方法执行后调用
    public void after(){
        session.commit();
        session.close();
    }

    @Test
    // 测试对一映射
    public void test1(){
        OrderMapper mapper = session.getMapper(OrderMapper.class);
        Order order = mapper.selectOrderByOrderId(1);
        System.out.println(order);
        System.out.println(order.getCustomer());
    }
    @Test
    // 测试对多映射
    public void test2(){
        CustomerMapper mapper = session.getMapper(CustomerMapper.class);
        Customer customer = mapper.selectCustomerByCustomerId(1);
        System.out.println(customer);
        System.out.println(customer.getList());
    }
}
