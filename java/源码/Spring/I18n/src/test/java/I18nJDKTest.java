import org.junit.jupiter.api.Test;

import java.util.Locale;
import java.util.ResourceBundle;

public class I18nJDKTest {
    @Test
    // 测试Java原生的国际化API
    public void test(){
        // 通过静态方法得到ResourceBundle对象
        ResourceBundle bundle1=ResourceBundle.getBundle("China",new Locale("zh","CH"));
        ResourceBundle bundle2=ResourceBundle.getBundle("message",new Locale("en","GB"));
        // 使用Bundle对象得到properties内对应的值
        String test1 = bundle1.getString("test");
        String test2 = bundle2.getString("test");
        // 输出
        System.out.println(test1);
        System.out.println(test2);
    }
}
