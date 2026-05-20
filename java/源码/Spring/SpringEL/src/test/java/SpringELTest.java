import com.example.Config;
import com.example.PropertiesValueSample;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.PropertySource;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@PropertySource(value = "")
public class SpringELTest {
    ApplicationContext context=new AnnotationConfigApplicationContext(Config.class);
    @Test
    // 测试properties文件读取
    public void test1(){
        PropertiesValueSample bean = context.getBean(PropertiesValueSample.class);
        System.out.println(bean.getAge());
        System.out.println(bean.getName());
        System.out.println(bean.getDescription());
    }

    @Test
    // 简单测试表达式
    public void test2(){
        ExpressionParser parser = context.getBean(ExpressionParser.class);  // 得到表达式分析器对象
        Expression expression = parser.parseExpression("'Hello World'");  // 得到表达式对象
        System.out.println(expression.getValue());  // 调用getValue方法得到表达式运行的值
    }

    @Test
    // 继续测试表达式
    public void test3(){
        ExpressionParser parser = context.getBean(ExpressionParser.class);  // 得到表达式分析器对象
        Expression exp1 = parser.parseExpression("'Hello World'.contains('H')");
        Expression exp2 = parser.parseExpression("new String('abc').charAt(1)");
        System.out.println(exp1.getValue());
        System.out.println(exp2.getValue());
    }

    @Test
    // 进行运算
    public void test4(){
        ExpressionParser parser = context.getBean(ExpressionParser.class);  // 得到表达式分析器对象
        Expression exp1 = parser.parseExpression("1+2*3/4");
        System.out.println(exp1.getValue());
    }

    @Test
    // 测试属性的修改
    public void test5(){
        ExpressionParser parser = context.getBean(ExpressionParser.class);  // 得到表达式分析器对象
        PropertiesValueSample bean = context.getBean(PropertiesValueSample.class);
        Expression exp1 = parser.parseExpression("name");
        // 注掉setter方法会报错，说明该方法底层通过setter方法来设置值
        exp1.setValue(bean,"yyy");
        System.out.println(bean.getName());
    }

    @Test
    // 全类名调用
    public void test6(){
        ExpressionParser parser = context.getBean(ExpressionParser.class);  // 得到表达式分析器对象
        // 对于类的调用，需要使用类的全类名，并使用 T(全类名) 的形式
        Expression exp1 = parser.parseExpression("T(java.util.Arrays).copyOfRange(new int[]{1,2,3,4,5,6},1,4)");
        Expression exp2 = parser.parseExpression("T(java.lang.String).valueOf(12)");
        System.out.println(exp1.getValue());
        System.out.println(exp2.getValue());
    }

    @Test
    // 集合类的快速读取与设置
    public void test7(){
        ExpressionParser parser = context.getBean(ExpressionParser.class);  // 得到表达式分析器对象
        PropertiesValueSample bean = context.getBean(PropertiesValueSample.class);
        Expression exp1 = parser.parseExpression("map['test']");
        Expression exp2 = parser.parseExpression("list[2]");
        // 这里是可以进行嵌套的，但本例由于泛型使用的是Map<String,String>来接收的，String没办法支持嵌套，不再演示
        // 这里的map基本上使用JS对象的声明格式即可
        Expression exp3 = parser.parseExpression("{test:'修改测试',name:'lzx'}");
        Expression exp4 = parser.parseExpression("{1,2,3,4,5,6}");
        Object val1 = exp1.getValue(bean);
        Object val2 = exp2.getValue(bean);
        Map<String,String> val3 = (Map<String,String>)exp3.getValue();
        List<Integer> val4=(List<Integer>)exp4.getValue();
        System.out.println(val1);
        System.out.println(val2);
        // 遍历Map，并输出键值对
        // Map也实现了toString方法，这里提供Stream流的遍历方法
        val3.entrySet().stream().forEach(
            (a)->{
                System.out.print("key = "+a.getKey()+" and value = "+a.getValue()+"\n");
            }
        );
        // list重写了toString方法，直接调用即可
        System.out.println(val4);

    }

    @Test
    // 测试集合的筛选
    public void test8(){
        ExpressionParser parser = context.getBean(ExpressionParser.class);  // 得到表达式分析器对象
        PropertiesValueSample bean = context.getBean(PropertiesValueSample.class);
        // 该表达式的意思但是：筛选出每个Map.Entry对象内的key属性是test字符串的键值对，加入map中
        // 换句话说，就是得到map的key为test的键值对，并将其放入新的map中
        /*
            他是这样判断的:
                遍历map对象
                map对象的迭代器返回的是包含着Node<K,V> implements Map.Entry的迭代器对象
                也就是说，遍历迭代器得到的每一个元素都是Map.Entry对象
                我们写的判断条件，就是判断这些Map.Entry对象类内部的key属性是否=test字符串
                也就是说，这样写的话，它判断的不是map内的每一个元素值，它判断的是map内的每一个元素值内的属性值
                它返回HashMap对象
                list集合同理，它判断的是list内的每一个元素的属性值，但返回ArrayList对象
         */
        Expression exp1 = parser.parseExpression("map.?[key=='test'&&value=='aaa']");
        System.out.println(exp1.getValue(bean));
    }

    @Test
    // 测试集合的投影
    public void test9(){
        ExpressionParser parser = context.getBean(ExpressionParser.class);  // 得到表达式分析器对象
        PropertiesValueSample bean = context.getBean(PropertiesValueSample.class);
        // 使用.!运算符进行解构，即把我们想得到的内部元素的值拿出来。读取的值与.?运算符一样，都是map中每个元素的内部属性值
        // []括号内需要写我们想获得的值的属性名称，以及它们的的组成结构
        // 如果想得到一个值，但是想得到键值对形式的值，键的名字就是key，值是想得到的值，可以写:{'key':key}
        // 如果想得到多个值封装在一个List内，可以像示例这样写:{key,value}
        // 只想得到一个值，直接写属性名即可
        // 它返回的是一个ArrayList对象
        // 如果想写多个值，使用{}包起来
        Expression exp1 = parser.parseExpression("map.![{'key':key,'value':value}]");
        Expression exp2 = parser.parseExpression("map.![{key,value}]");
        System.out.println(exp1.getValue(bean));
        System.out.println(exp2.getValue(bean));
    }

    @Test
    // 判断空值
    public void test10(){
        ExpressionParser parser = context.getBean(ExpressionParser.class);  // 得到表达式分析器对象
        PropertiesValueSample bean = context.getBean(PropertiesValueSample.class);
        bean.setName(null);
        // ?.可选链运算符(MDN的叫法) 用来判断前一个值是否为空，若为空那么返回null，否则继续执行下面的语句
        Expression exp = parser.parseExpression("name?.contains('a')");
        System.out.println(exp.getValue(bean));
    }
}
