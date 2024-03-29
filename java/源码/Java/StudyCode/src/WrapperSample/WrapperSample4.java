package WrapperSample;

public class WrapperSample4 {
    public static void main(String[] args) {
        Object o1 = true ? new Integer(1) : new Double(2.0);
        System.out.println(o1);//1.0,这时候需要返回表示范围大的，如果二者没有可比性且至少一个为对象，那么返回对应的对象或包装类对象。如果没有对象，自动类型提升后返回
        System.out.println((true?1:2.0));
        Object o2;
        if (true)
            o2 = new Integer(1);
        else
            o2 = new Double(2.0);
        System.out.println(o2);// 此时输出调用Integer类重写后的toString方法得到返回值，输出1
    }
}
