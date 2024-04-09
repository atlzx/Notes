import com.example.Example;
import com.example.User;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class ApplicationContextTest {
    @Test
    // 测试通配符
    public void test1(){
        // 使用classpath* 来匹配类路径下所有满足后面条件的xml文件
        // 如果不使用classpath*,那么只会匹配第一个符合条件的文件
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath*:bean*.xml");
        Example example = context.getBean("example", Example.class);
        User user = context.getBean("user", User.class);
        user.study();
        example.run();
    }
}
