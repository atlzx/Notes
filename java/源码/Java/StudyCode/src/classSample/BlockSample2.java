package classSample;

class BlockSampleRoot{
    static{
        System.out.println("Root的静态初始化块");
    }
    {
        System.out.println("Root的普通初始化块");
    }
    public BlockSampleRoot(){
        super();
        System.out.println("Root的无参数的构造器");
    }
}
class BlockSampleMid extends BlockSampleRoot{
    static{
        System.out.println("Mid的静态初始化块");
    }
    {
        System.out.println("Mid的普通初始化块");
    }
    public BlockSampleMid(){
        System.out.println("Mid的无参数的构造器");
    }
    public BlockSampleMid(String msg){
        //通过this调用同一类中重载的构造器
        this();
        System.out.println("Mid的带参数构造器，其参数值："
                + msg);
    }
}
class BlockSampleLeaf extends BlockSampleMid{
    static{
        System.out.println("Leaf的静态初始化块");
    }
    {
        System.out.println("Leaf的普通初始化块");
    }
    public BlockSampleLeaf(){
        //通过super调用父类中有一个字符串参数的构造器
        super("尚硅谷");
        System.out.println("Leaf的构造器");
    }
}
public class BlockSample2{
    public static void main(String[] args){
        // 请说明下面的代码的执行结果
        //技巧：由父及子，静态先行。
        new BlockSampleLeaf();
    }
}
