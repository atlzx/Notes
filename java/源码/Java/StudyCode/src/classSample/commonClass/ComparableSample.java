package classSample.commonClass;

import java.util.Arrays;

public class ComparableSample {
    public static void main(String[] args) {
        ComparableSample1 sample1=new ComparableSample1("测试1",12);
        ComparableSample1 sample2=new ComparableSample1("测试1",15);
        ComparableSample1 sample3=new ComparableSample1("测试2",12);
        ComparableSample1 sample4=new ComparableSample1("测试3",14);
        ComparableSample1 sample5=new ComparableSample1("测试4",10);
        ComparableSample1[] arr=new ComparableSample1[]{sample1,sample2,sample3,sample4,sample5};
        Arrays.sort(arr);  // Arrays.sort方法在给元素排序时，会针对数组中的元素调用其compareTo方法
        for (int i = 0; i < arr.length; i++) {
            System.out.println(arr[i].name+" "+arr[i].age);
        }
    }

}

// Comparable是接口，需要被类实现
class ComparableSample1 implements Comparable{
    String name;
    int age;

    public ComparableSample1(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public ComparableSample1() {

    }

    @Override
    public int compareTo(Object o) {
        // 在此处定义排序规则，这里是先按名字的Unicode编码排序，如果相等再按年龄升序排序，如果全相等返回0
        if(o instanceof ComparableSample1){
            ComparableSample1 sample1=(ComparableSample1) o;
            if(this.name.compareTo(sample1.name)>0){
                return 1;
            }else if(this.name.compareTo(sample1.name)<0){
                return -1;
            }else{
                if(this.age> sample1.age){
                    return 1;
                }else if(this.age< sample1.age){
                    return -1;
                }
                return 0;
            }
        }
        throw new RuntimeException("类型不匹配");
    }
}
