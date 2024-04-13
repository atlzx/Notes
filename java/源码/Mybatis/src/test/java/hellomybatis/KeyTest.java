package hellomybatis;

import com.mybatis.example.hellomybatis.mapper.EmployeeMapper;
import com.mybatis.example.hellomybatis.mapper.TeacherMapper;
import com.mybatis.example.hellomybatis.pojo.Employee;
import com.mybatis.example.hellomybatis.pojo.Teacher;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;

public class KeyTest {
    private SqlSession session;

    @BeforeEach
    // @BeforeEach注解使作用方法在每个方法执行前调用
    public void before() throws IOException {
        InputStream in = Resources.getResourceAsStream("hellomybatis/mybatis-config.xml");
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
    // 测试主键回显
    public void test1(){
        Employee employee = new Employee();
        employee.setEmpName("aaaa");
        employee.setEmpSalary(50.0);
        EmployeeMapper mapper = session.getMapper(EmployeeMapper.class);
        System.out.println("插入前:"+employee.getEmpId());
        mapper.generatedKeyInsert(employee);
        System.out.println("插入后:"+employee.getEmpId());
    }

    @Test
    // 测试非主键插入
    public void test2(){
        Teacher teacher = new Teacher();
        teacher.setTeacherAge(18);
        teacher.setTeacherName("lzx");
        TeacherMapper mapper = session.getMapper(TeacherMapper.class);
        mapper.uuidInsert(teacher);
    }
}
