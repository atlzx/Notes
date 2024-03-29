package genericity;

import org.junit.Test;

public class GenericitySample4 {
    @Test
    public void test1(){
        GenericitySample4_3 genericitySample4_3=new GenericitySample4_3();
        genericitySample4_3.age=1;  // 由于该子类对象默认指定了继承父类对象的详细泛型类型，因此该属性为Integer类型
        System.out.println(genericitySample4_3.age.getClass());
        GenericitySample4_4<String> genericitySample4_4=new GenericitySample4_4<>();
        genericitySample4_4.age="111";
        GenericitySample4_5<String,Integer> genericitySample4_5=new GenericitySample4_5();
        genericitySample4_5.name="lzx";
        // genericitySample4_5.age=12;  继承过来的父类与子类指定的泛型是一致的
        genericitySample4_5.score=12;
    }
}

class GenericitySample4_1<E>{
    E age;
}

// 这样写编译会报错，解决方法是要么给子类也带上泛型，要么去掉继承父类的泛型
//class GenericitySample4_2 extends GenericitySample4_1<E>{ }

// 直接指定父类的泛型详细类型，这样创建该类对象时，编译器会知道其继承的父类对象被其泛型修饰的属性是Integer类型的
class GenericitySample4_3 extends GenericitySample4_1<Integer>{
    String name;
}

// 子类继承父类的泛型，生成该类对象时，指定其泛型后，其父类对象泛型将与子类一致
class GenericitySample4_4<E> extends GenericitySample4_1<E>{
    E name;
}

class GenericitySample4_5<E,T> extends GenericitySample4_1<E>{
    E name;
    T score;
}