import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.core.io.Resource;

import java.io.InputStream;
import java.util.Arrays;

public class ResourceLoaderTest {
    @Test
    public void test1() throws Exception{
        ApplicationContext context = new ClassPathXmlApplicationContext("bean.xml");
        Resource resource = context.getResource("ResourceLoader.txt");
        InputStream in = resource.getInputStream();
        byte[] bytes=new byte[1024];
        while(true){
            int num = in.read(bytes);
            if(num==-1){
                break;
            }
            System.out.println(new String(Arrays.copyOfRange(bytes,0,num)));
        }
        System.out.println(resource.getClass());  // 通过输出可以看到得到的是ClassPathContextResource类
    }


    @Test
    public void test2() throws Exception{
        FileSystemXmlApplicationContext context = new FileSystemXmlApplicationContext("src\\main\\resources\\bean.xml");
        Resource resource = context.getResource("src\\main\\resources\\ResourceLoader.txt");
        InputStream in = resource.getInputStream();
        byte[] bytes=new byte[1024];
        while(true){
            int num = in.read(bytes);
            if(num==-1){
                break;
            }
            System.out.println(new String(Arrays.copyOfRange(bytes,0,num)));
        }
        System.out.println(resource.getClass());  // 通过输出可以看到得到的是FileSystemResource类

    }
}
