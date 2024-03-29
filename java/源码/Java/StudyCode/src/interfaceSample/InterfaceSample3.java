package interfaceSample;

public interface InterfaceSample3 {
    // 该方法用来测试与第一个还有第二个接口之间的方法冲突，其中包含了同种方法之间的同名冲突以及抽象方法和默认方法之间的冲突
    void show1();
    public default void show2(){
        System.out.println("这是第三个接口内的默认方法");
    }
}
