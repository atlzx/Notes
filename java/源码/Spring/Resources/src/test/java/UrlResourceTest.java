import org.junit.jupiter.api.Test;
import org.springframework.core.io.UrlResource;

public class UrlResourceTest {
    @Test
    // 测试urlResource的一些方法，顺便也作为Resource接口的方法示例
    public void test1() throws Exception{
        UrlResource urlResource = new UrlResource("http://www.baidu.com");
        System.out.println(urlResource.getDescription());
        System.out.println(urlResource.getFilename());
        System.out.println(urlResource.getURL());
        System.out.println(urlResource.contentLength());
        System.out.println(urlResource.lastModified());
    }
}
