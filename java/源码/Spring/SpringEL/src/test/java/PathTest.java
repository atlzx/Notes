import org.junit.jupiter.api.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class PathTest {
    @Test
    public void test1(){
        // 匹配前缀是a的文件
        // 必须写.xxx指明文件类型，否则spring识别不出来
        // 必须写classpath*,因为classpath:仅加载匹配到的第一个资源
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath*:a*.xml");
        Object bean3 = context.getBean("a3");
        System.out.println(bean3);
        Object bean1 = context.getBean("a1");
        System.out.println(bean1);
        Object bean2 = context.getBean("a2");
        System.out.println(bean2);
    }

    @Test
    public void test2(){
        // 匹配前缀是a，后缀是b的文件
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath*:a*b.xml");
        Object bean3 = context.getBean("a3");
        System.out.println(bean3);
        Object bean1 = context.getBean("a1");
        System.out.println(bean1);
        Object bean2 = context.getBean("a2");
        System.out.println(bean2);
    }
    @Test
    public void test3(){
        // 匹配后缀是a的文件
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath*:*a.xml");
        Object bean2 = context.getBean("a2");
        System.out.println(bean2);
        Object bean3 = context.getBean("a3");
        System.out.println(bean3);
        Object bean1 = context.getBean("a1");
        System.out.println(bean1);

    }
}
