import com.example.Config;
import com.example.annovalidation.Person;
import jakarta.annotation.Resource;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;

import java.util.List;
import java.util.Set;

public class AnnotationValidationTest {
    private final ApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
    @Test
    // 使用jakarta的Validator对象
    public void test1(){
        // 测试类中无法自动装配，需要自己得到
        Validator validator = context.getBean("validator", Validator.class);
        Person bean = context.getBean(Person.class);
        bean.setName("");
        bean.setEmail("1928564318@qq.com");
        bean.setHobby(null);
        bean.setAge(-1);
        // 调用validate方法执行校验
        Set<ConstraintViolation<Person>> validate = validator.validate(bean);
        if(validate.isEmpty()){
            System.out.println("校验未出现问题");
        }else{
            ConstraintViolation<Person>[] obj = new ConstraintViolation[validate.size()];
            ConstraintViolation<Person>[] arr = validate.toArray(obj);
            for(ConstraintViolation<Person> i:arr){
                System.out.println(i.getMessage());
            }
        }

    }

    @Test
    public void test2(){
        Person bean = context.getBean(Person.class);
        org.springframework.validation.Validator validator = context.getBean("validator", org.springframework.validation.Validator.class);
        bean.setAge(150);
        bean.setEmail("111");
        bean.setHobby(null);
        bean.setName("lzx");
        // BindException是Errors接口的实现类之一
        BindException bindException=new BindException(bean,bean.getName());
        validator.validate(bean,bindException);
        List<ObjectError> list = bindException.getBindingResult().getAllErrors();
        if(list.isEmpty()){
            System.out.println("校验结束，未发现错误");
        }else{
            for(ObjectError i:list){
                System.out.println(i.getObjectName()+"出现错误:"+i.getDefaultMessage());
                System.out.println(i);
            }
        }
    }
}
