import com.example.Config;
import com.example.MethodValidateSample;
import com.example.User;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MethodValidationTest {
    @Test
    public void test(){
        ApplicationContext context=new AnnotationConfigApplicationContext(Config.class);
        User user = context.getBean(User.class);
        user.setAge(5);
        user.setName(null);
        MethodValidateSample validator = context.getBean(MethodValidateSample.class);
        String str = validator.validate(user);
        System.out.println(str);
    }
}
