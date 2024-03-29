package annotationSample;

import java.lang.annotation.*;

public class AnnotationSample1 {
    @myAnnotation(value2="ccc")
    public static void main(String[] args) {

    }
}

// 自定义注解的声明
@Documented  // 声明该注解需要被javadoc识别，且被写进API文档内
@Target(ElementType.METHOD)  // 声明作用对象为方法且仅为方法
@Inherited  // 声明子类可以继承父类的注解
@Retention(RetentionPolicy.RUNTIME)  // 声明该注解在运行时可以被读取
@interface myAnnotation{
    String[] value1() default {"aaa","bbb"};  // 使用default关键字给抽象方法赋初值
    String value2();
}
