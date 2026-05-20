package classSample;

class BlockSampleHelloA {
    public BlockSampleHelloA() {
        System.out.println("HelloA");
    }

    {
        System.out.println("I'm A Class");
    }

    static {
        System.out.println("static A");
    }
}

class BlockSampleHelloB extends BlockSampleHelloA {
    public BlockSampleHelloB() {
        System.out.println("HelloB");
    }

    {
        System.out.println("I'm B Class");
    }

    static {
        System.out.println("static B");
    }


}

public class BlockSample3 {
    public static void main(String[] args) {
        new BlockSampleHelloB();
    }
}
