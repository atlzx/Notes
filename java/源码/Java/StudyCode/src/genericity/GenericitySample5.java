package genericity;

import org.junit.Test;

import java.util.*;

public class GenericitySample5 {
    @Test
    public void test1(){
        Map<String,Integer> map=new HashMap<>();  // 构造时new后面的构造器省略写泛型确切类型是JDK7新增的特性
        map.put("aaa",12);
        map.put("张三",18);
        // map.put(11,"aaa"); 因为指定了泛型类型，因此这样写就编译不通过了
        Integer i = map.get("aaa");  // 编译器也会得知该对象的对应方法的详细返回类型
        var entrySet = map.entrySet();  // 可以使用var来代替声明有准确返回值类型的变量类型
        // 遍历Set
        for(Map.Entry<String,Integer> entry:entrySet){
            System.out.println(entry);
        }
    }
    @Test
    public void test2(){
        List<GenericitySample5_1> list=new ArrayList<>();
        list.add(new GenericitySample5_1("张三",18));
        list.add(new GenericitySample5_1("李四",18));
        list.add(new GenericitySample5_1("aaa",12));
        Collections.sort(list);
        for(GenericitySample5_1 genericitySample5_1:list){
            System.out.println(genericitySample5_1);
        }
    }

    @Test
    public void test3(){
        List<GenericitySample5_1> list=new ArrayList<>();
        list.add(new GenericitySample5_1("张三",18));
        list.add(new GenericitySample5_1("李四",18));
        list.add(new GenericitySample5_1("aaa",12));
        Collections.sort(
            list,
            // 使用匿名内部类传入Comparator对象
            new Comparator<GenericitySample5_1>() {
                // 此处也是根据年龄从小到大，再根据名称从大到小进行排序
                @Override
                public int compare(GenericitySample5_1 o1, GenericitySample5_1 o2) {
                    int result1=o1.getAge()-o2.getAge();
                    if(result1!=0){
                        return result1;
                    }else{
                        return -o1.getName().compareTo(o2.getName());
                    }
                }
            }
        );
        for(GenericitySample5_1 genericitySample5_1:list){
            System.out.println(genericitySample5_1);
        }
    }
}

// Comparable接口在传递泛型类型时，要与本对象比较的是哪个类型，就传哪个类型
class GenericitySample5_1 implements Comparable<GenericitySample5_1>{
    private String name;
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public GenericitySample5_1() {

    }

    public GenericitySample5_1(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GenericitySample5_1 that = (GenericitySample5_1) o;

        if (age != that.age) return false;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + age;
        return result;
    }

    @Override
    public String toString() {
        return "GenericitySample5_1{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    // 先根据年龄从小到大排序，相等再根据名字从大到小排序
    @Override
    public int compareTo(GenericitySample5_1 o) {
        int i = this.age - o.age;
        if(i!=0){
            return i;
        }else{
            return -(this.name.compareTo(o.name));
        }
    }
}
