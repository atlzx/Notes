package classSample;

public class BlockSample5 {
    public static void main(String[] args) {
        // 看看输出什么
        BlockSampleSub s = new BlockSampleSub();
    }
}
class BlockSampleBase{
    BlockSampleBase(){
        method(100);  // 这个method是this.method,而this指向当前对象，即new创建的对象，因此调用的是重写后的方法，输出sub:100
    }
    {
        System.out.println("base");
    }
    public void method(int i){
        System.out.println("base : " + i);
    }
}
class BlockSampleSub extends BlockSampleBase{
    BlockSampleSub(){
        super.method(70);  // 这行代码执行之前，要先执行七后面的代码块
    }
    {
        System.out.println("sub");
    }
    public void method(int j){
        System.out.println("sub : " + j);
    }
}