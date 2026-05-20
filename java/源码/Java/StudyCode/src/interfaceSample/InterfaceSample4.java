package interfaceSample;

public interface InterfaceSample4 extends InterfaceSample1,InterfaceSample2,InterfaceSample3{

    @Override
    void show1();

    @Override
    default void show2() {
        InterfaceSample1.super.show2();
    }

    @Override
    default void show2(String str) {
        InterfaceSample1.super.show2(str);
    }
}
