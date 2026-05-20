package interfaceSample;


public class InterfaceSampleImpl2 {

}

interface InterfaceSampleImpl2_1 {
    int x = 0;
}
class InterfaceSampleImpl2_2 {
    int x = 1;
}
class InterfaceSampleImpl2_3 extends InterfaceSampleImpl2_2 implements InterfaceSampleImpl2_1 {
    public void pX() {
        // System.out.println(x);  // 对x的引用不明确
    }
    public static void main(String[] args) {
        // 排错
        new InterfaceSampleImpl2_3().pX();
    }
}