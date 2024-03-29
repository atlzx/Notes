package interfaceSample;
public class InterfaceSampleImpl3 {
    public static void main(String[] args) {
        // 查错
    }

}


interface InterfaceSampleImpl3_1 {
    void play();
}

interface InterfaceSampleImpl3_2 {
    void play();
}

interface InterfaceSampleImpl3_3 extends InterfaceSampleImpl3_1, InterfaceSampleImpl3_2 {
    InterfaceSampleImpl3_4 ball = new InterfaceSampleImpl3_4("PingPang");

}

class InterfaceSampleImpl3_4 implements InterfaceSampleImpl3_3 {
    private String name;

    public String getName() {
        return name;
    }

    public InterfaceSampleImpl3_4(String name) {
        this.name = name;
    }

    public void play() {
        // ball = new InterfaceSampleImpl3_4("Football");  接口内变量一旦被初始化便不能修改，因为被final修饰
        System.out.println(ball.getName());
    }
}