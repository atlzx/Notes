import com.example.Config;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Date;
import java.util.Locale;


public class I18nSpringTest {
    @Test
    public void test(){
        ApplicationContext context = new ClassPathXmlApplicationContext("bean.xml");
        Object[] obj1=new Object[]{"中国",new Date().toString()};
        Object[] obj2=new Object[]{"英国",new Date().toString()};
        String str2 = context.getMessage("test2", obj2, new Locale("en","GB"));
        String str1 = context.getMessage("test2", obj1, new Locale("zh","CN"));
        System.out.println(str1);
        System.out.println(str2);
    }
}
