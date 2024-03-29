package genericity;

public class GenericitySample2<T> {
    // 方法是否为泛型方法取决于其返回值前面或修饰符后面有没有泛型标识，即类似<T>的标志
    // 泛型方法的泛型类型并不取决于其所属类的泛型类型，而是由其返回值前面的泛型标识决定
    // 泛型方法可以被static修饰，因为它的泛型类型直到被调用时才会被确定
    public static <T> T test1(T a){
        return a;
    }

    // 该方法不属于泛型方法，如果参数类型为泛型，那么该类型则取决于所属类的泛型约束
    public T test2(T a){
        return a;
    }

    public static void main(String[] args) {
        GenericitySample2<Integer> genericitySample2=new GenericitySample2<>();
        System.out.println(GenericitySample2.test1("aaa").getClass());  // String
        System.out.println(GenericitySample2.test1(1).getClass());  // Integer
//        genericitySample2.test2("aaa");  // 由于该对象的泛型约束是Integer，因此方法只能接收Integer类型的参数
        genericitySample2.test2(1);  // 由于该对象的泛型约束是Integer，因此方法只能接收Integer类型的参数

    }
}
