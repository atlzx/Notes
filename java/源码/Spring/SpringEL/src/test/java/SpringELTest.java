import com.example.Config;
import com.example.PropertiesValueSample;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.PropertySource;

@PropertySource(value = "")
public class SpringELTest {
    ApplicationContext context=new AnnotationConfigApplicationContext(Config.class);
    @Test
    // 测试properties文件读取
    public void test1(){
        PropertiesValueSample bean = context.getBean(PropertiesValueSample.class);
        System.out.println(bean.getAge());
        System.out.println(bean.getName());
        System.out.println(bean.getDescription());
    }
}
