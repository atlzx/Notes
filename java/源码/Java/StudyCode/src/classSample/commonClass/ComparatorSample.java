package classSample.commonClass;

import org.junit.Test;

import java.util.Arrays;
import java.util.Comparator;

public class ComparatorSample {
    @Test
    public void test1(){
        ComparatorSample1 sample1=new ComparatorSample1("测试1",12);
        ComparatorSample1 sample2=new ComparatorSample1("测试1",15);
        ComparatorSample1 sample3=new ComparatorSample1("测试2",12);
        ComparatorSample1 sample4=new ComparatorSample1("测试3",14);
        ComparatorSample1 sample5=new ComparatorSample1("测试4",10);
        ComparatorSample1[] arr=new ComparatorSample1[]{sample1,sample2,sample3,sample4,sample5};
        Arrays.sort(arr,
                    // 使用匿名内部类直接创建Comparator接口的实现类
                    new Comparator() {
                        @Override
                            public int compare(Object o1, Object o2) {
                                // 在此处定义排序规则，这里是先按名字的Unicode编码排序，如果相等再按年龄升序排序，如果全相等返回0
                                if(o1 instanceof ComparatorSample1 && o2 instanceof ComparatorSample1){
                                    ComparatorSample1 sample1=(ComparatorSample1) o1;
                                    ComparatorSample1 sample2=(ComparatorSample1) o2;
                                    if(sample1.name.compareTo(sample2.name)>0){
                                        return 1;
                                    }else if(sample1.name.compareTo(sample2.name)<0){
                                        return -1;
                                    }else{
                                        if(sample1.age>sample2.age){
                                            return 1;
                                        }else if(sample1.age<sample2.age){
                                            return -1;
                                        }
                                        return 0;
                                    }
                                }
                                throw new RuntimeException("传入的对象类型不匹配");
                            }
                        }
        );  // 调用Arrays.sort方法时，向其传递的第二个参数为Comparator实现类对象时，排序时便会使用该对象内的compare方法来比较数组中的两个对象
        for (int i = 0; i < arr.length; i++) {
            System.out.println(arr[i].name+" "+arr[i].age);
        }
    }
}

class ComparatorSample1{
    String name;
    int age;

    public ComparatorSample1() {
    }

    public ComparatorSample1(String name, int age) {
        this.name = name;
        this.age = age;
    }
}
