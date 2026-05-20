import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.UrlResource;

import java.io.InputStream;
import java.util.Arrays;

public class UrlResourceTest {
    @Test
    // 测试urlResource的一些方法，顺便也作为Resource接口的方法示例
    public void test1() throws Exception {
        UrlResource urlResource = new UrlResource("http://www.baidu.com"); // 获取网络资源
        // 这里演示的是相对路径得到资源对象，它是相对于项目根路径的
        // 在测试类中，urlResource如果使用相对路径，该相对路径是相对于当前项目根路径的
        // 在其它类下，urlResource如果使用相对路径，该相对路径是相对于最层项目根路径的
        UrlResource urlResource1 = new UrlResource("file:src\\main\\resources\\UrlResource.txt");

        System.out.println(urlResource.getDescription());
        System.out.println(urlResource.getFilename());
        System.out.println(urlResource.getURL());
        System.out.println(urlResource.contentLength());
        System.out.println(urlResource.lastModified());
        System.out.println("-----------------------------------------------------------");
        System.out.println(urlResource1.getDescription());
        System.out.println(urlResource1.getFilename());
        System.out.println(urlResource1.getURL());
        System.out.println(urlResource1.contentLength());
        System.out.println(urlResource1.lastModified());
        // 读取文件内容
        InputStream in = urlResource1.getInputStream();
        byte[] bytes=new byte[1024];
        while(true){
            int number = in.read(bytes);
            if(number==-1){
                break;
            }
            // 通过copyOfRange方法来进行数组的切片
            byte[] bytes1 = Arrays.copyOfRange(bytes, 0, number);
            System.out.println(new String(bytes1));
        }
    }

    @Test
    // 测试ClassPathResource的一些方法，顺便也作为Resource接口的方法示例
    public void test2() throws Exception{
        ClassPathResource classPathResource=new ClassPathResource("classPathResource.txt");
        System.out.println(classPathResource.getDescription());
        System.out.println(classPathResource.getFilename());
        System.out.println(classPathResource.getURL());
        System.out.println(classPathResource.contentLength());
        System.out.println(classPathResource.lastModified());
        InputStream in = classPathResource.getInputStream();
        byte[] bytes=new byte[1024];
        while(true){
            int number = in.read(bytes);
            if(number==-1){
                break;
            }
            // 通过copyOfRange方法来进行数组的切片
            byte[] bytes1 = Arrays.copyOfRange(bytes, 0, number);
            System.out.println(new String(bytes1));
        }
    }

    @Test
    // 测试FileSystemResource的一些方法，顺便也作为Resource接口的方法示例
    public void test3() throws Exception{
        // 在测试类中，FileSystemResource如果使用相对路径，该相对路径是相对于当前项目根路径的
        // 在其它类下，FileSystemResource如果使用相对路径，该相对路径是相对于最层项目根路径的
        FileSystemResource fileSystemResource=new FileSystemResource("src\\main\\resources\\UrlResource.txt");
        System.out.println(fileSystemResource.getDescription());
        System.out.println(fileSystemResource.getFilename());
        System.out.println(fileSystemResource.getURL());
        System.out.println(fileSystemResource.contentLength());
        System.out.println(fileSystemResource.lastModified());
        InputStream in = fileSystemResource.getInputStream();
        byte[] bytes=new byte[1024];
        while(true){
            int number = in.read(bytes);
            if(number==-1){
                break;
            }
            // 通过copyOfRange方法来进行数组的切片
            byte[] bytes1 = Arrays.copyOfRange(bytes, 0, number);
            System.out.println(new String(bytes1));
        }
    }
}
