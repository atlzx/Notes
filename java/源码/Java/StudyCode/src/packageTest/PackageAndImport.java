
package packageTest;  // package会指定该类所属的包
// import static组合使用可以调用指定类或接口下的静态的属性或方法

import java.util.Date;

import static java.lang.System.out;

public class PackageAndImport {
    public static void main(String[] args) {
        out.println("测试");  // 使用import引入out后，可以直接调用out了
        Date date1=new Date();
        java.sql.Date date2=new java.sql.Date(123456789123L);  // 如果我们想在一段代码内使用两个或多个重名的类，我们需要使用类的全名调用他们
    }
}
