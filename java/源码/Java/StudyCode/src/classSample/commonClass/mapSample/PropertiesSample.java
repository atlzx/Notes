package classSample.commonClass.mapSample;

import org.junit.Test;

import java.io.*;
import java.util.Properties;

public class PropertiesSample {
    @Test
    public void test1() throws Exception {
        Properties properties=new Properties();
        // 单元测试方法的相对路径是相对于所在模块(Module)的
        File file=new File("src/classSample/commonClass/mapSample/pro.properties");
        Reader reader=new FileReader(file);  // 得到字符输入流对象
        properties.load(reader);  // 使用load方法加载文件信息
        String name = properties.getProperty("name");
        String age=properties.getProperty("age");
        String gender=properties.getProperty("gender");
        System.out.println(name);
        System.out.println(age);
        System.out.println(gender);
        // 使用setProperty方法貌似不会修改文件的键值对，但会使下面的getProperty得到修改后的值，应该是将这些键值对加载到内存中才修改的，与硬盘无关
        properties.setProperty("name","李二狗");
        System.out.println(properties.getProperty("name"));
    }
}
