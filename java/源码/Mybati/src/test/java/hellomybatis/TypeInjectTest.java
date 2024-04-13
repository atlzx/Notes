package hellomybatis;

import com.mybatis.example.hellomybatis.mapper.EmployeeMapper;
import com.mybatis.example.hellomybatis.pojo.Employee;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpression;
import org.springframework.expression.spel.standard.SpelExpressionParser;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class TypeInjectTest {
    @Test
    // 测试类型传入与结果输出
    public void test() throws IOException {
        // 得到mybatis-config.xml的输入流对象
        InputStream in = Resources.getResourceAsStream("hellomybatis/mybatis-config.xml");
        // 得到生成事务对象的工厂对象
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(in);
        // openSession传入一个boolean值参数时，表示每次执行相关sql操作，都自动提交，即开启自动提交
        SqlSession sqlSession = factory.openSession(true);
        // 调用会话的getMapper方法，来得到接口的代理对象
        EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
        // 得到执行结果
        List<Employee> employees = mapper.selectSlicedEmployee(1, 3);
        // 使用SpEL表达式生成Map
        SpelExpressionParser parser = new SpelExpressionParser();
        Expression exp = parser.parseExpression("""
            {
                name:'Lucy',
                salary:555.55
            }
            """);
        Map<String, String> resultMap = mapper.selectInfoByMap((Map<String, Object>) exp.getValue());
        System.out.println(employees);
        System.out.println(resultMap);
    }
}
