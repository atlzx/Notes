package dynamicsql;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mybatis.example.dynamicsql.mapper.EmployeeMapper;
import com.mybatis.example.dynamicsql.pojo.Employee;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class DynamicSQLTest {
    private SqlSession session;
    @BeforeEach
    // @BeforeEach注解使作用方法在每个方法执行前调用
    public void before() throws IOException {
        InputStream in = Resources.getResourceAsStream("dynamicsql/mybatis-config.xml");
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
    // 测试where和if标签
    public void test1(){
        EmployeeMapper mapper = session.getMapper(EmployeeMapper.class);
        Employee emp = mapper.selectByNameAndSalary("jerry", 888.88);
        System.out.println(emp);
    }

    @Test
    // 测试choose、when、otherwise标签
    public void test2(){
        EmployeeMapper mapper = session.getMapper(EmployeeMapper.class);
        List<Employee> employee = mapper.selectByNameOrSalary(null, 500.0);
        System.out.println(employee);
    }

    @Test
    // 测试foreach标签
    public void test3(){
        List<Employee> list=new ArrayList<>();
        list.add(new Employee(null,"test1",999.99));
        list.add(new Employee(null,"test2",1000.0));
        EmployeeMapper mapper = session.getMapper(EmployeeMapper.class);
        int result = mapper.batchInsertEmployee(list);
        System.out.println(result);
    }

    @Test
    // 测试set标签
    public void test4(){
        EmployeeMapper mapper = session.getMapper(EmployeeMapper.class);
        int result = mapper.updateEmployeeInfoById(null, 1200.0, 1);
        System.out.println(result);
    }

    @Test
    // 测试sql和include标签，并测试分页插件
    public void test5(){
        EmployeeMapper mapper = session.getMapper(EmployeeMapper.class);
        // 调用PageHelper类的静态方法，指定想得到的页码以及每页的数据数量
        // 该方法会作用于离它最近的下面的被调用的查询语句
        PageHelper.startPage(2, 2);
        List<Employee> list = mapper.selectPage();
        System.out.println(list);
        // 通过PageInfo得到页对象
        PageInfo<Employee> pageInfo = new PageInfo<>(list);
        System.out.println("每页的数据量:"+pageInfo.getPageSize());
        System.out.println("总页数:"+pageInfo.getPages());
        System.out.println("当前页包含的数据量:"+pageInfo.getSize());
        System.out.println("页的List集合:"+pageInfo.getList());
        System.out.println("数据总量:"+pageInfo.getTotal());
        System.out.println("本页的最后一行数据的下标:"+pageInfo.getEndRow());
        System.out.println("本页的第一行数据的下标:"+pageInfo.getStartRow());
    }
}
