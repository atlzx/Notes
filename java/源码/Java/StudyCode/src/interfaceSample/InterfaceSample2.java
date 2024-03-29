package interfaceSample;

public interface InterfaceSample2 {
    // 该接口用来测试与第一个接口冲突时，且是默认方法和抽象方法之间的冲突
    void show2();
    void show2(String str);
    public default void show1(){
        System.out.println("这是第二个接口内的默认方法");
    }
}
