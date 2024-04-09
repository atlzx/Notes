import com.example.Config;
import com.example.InterfaceValidationSample;
import com.example.People;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.validation.ObjectError;

import java.util.List;

public class InterfaceValidationTest {
    private ApplicationContext context=new AnnotationConfigApplicationContext(Config.class);
    @Test
    // 测试接口校验方式
    public void test(){
        People bean = context.getBean(People.class);
        bean.setName("Lucy");
        bean.setAge(201);
        // 生成一个专门校验该bean对象的dataBinder
        DataBinder dataBinder = new DataBinder(bean);

        // 向dataBinder传入指定的校验器
        dataBinder.setValidator(context.getBean(InterfaceValidationSample.class));

        // 执行校验
        dataBinder.validate();

        // 得到校验后的结果
        BindingResult result = dataBinder.getBindingResult();

        // 得到全部的错误List对象
        List<ObjectError> list = result.getAllErrors();

        if(list==null||list.size()==0){
            System.out.println("校验完成，未发现错误");
        }else{
            for(ObjectError error:list){
                System.out.println("发现错误:"+error.toString());
            }
        }


    }
}
