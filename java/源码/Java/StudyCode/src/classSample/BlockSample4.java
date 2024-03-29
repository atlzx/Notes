package classSample;

public class BlockSample4 {
    static int x, y, z;

    static {
        int x = 5;
        x--;  // 这个x减的是代码块的5，与静态变量无关
    }

    static {
        x--;  // 此时x=-1
    }

    public static void method() {
        y = z++ + ++z;  // -1 + 1 = 0 ,所以y=0,z=1
    }

    public static void main(String[] args) {
        System.out.println("x=" + x);
        z--;  // z=-1
        method();
        System.out.println("result:" + (z + y + ++z));  // 1+0+2=3
    }


}