package classSample;

public class AssignmentSample {

    String name="aaa";

    // 如果把代码块写在显式初始化之后，那么代码块的赋值顺序会在显式赋值之后，但在构造器执行之前
    // 如果把代码块写在显式初始化之前，那么代码块的赋值顺序会在显式赋值之前，但写在前面无法读取变量，只能修改
    {
        System.out.println("代码块内输出当前name值:"+name);
        name="bbb";
        System.out.print("输出代码块内修改后的name值:");
        show1();

    }

    public AssignmentSample(String name){
        System.out.println("构造器内输出当前name值:"+this.name);
        this.name=name;
    }

    public static void main(String[] args) {
        AssignmentSample assignmentSample=new AssignmentSample("ccc");
        System.out.println("main方法内输出"+assignmentSample.name);
        assignmentSample.name="ddd";
        System.out.println("main方法内修改后输出"+assignmentSample.name);
    }
    public void show1(){
        System.out.println(name);
    }
}
