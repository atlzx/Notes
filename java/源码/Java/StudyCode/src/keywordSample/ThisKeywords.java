package keywordSample;

public class ThisKeywords {
    public static void main(String[] args) {
        Test test=new Test();
        int number = test.getNumber();
        System.out.println(number);
    }
}


class Test{
    int number;
    String name;
    public Test(){
        // this(1,"aaa");  这样写会使构造器之间相互调用，从而导致编译错误
    }

    public Test(int number){
        this();  // this可以直接调用除自己之外的构造器
        this.number=number;  // 当形参名称与属性名冲突时，可以使用this进行区分
    }

    public Test(int number,String name){
        this(number);  // this可以直接调用除自己之外的构造器
        this.name=name;
        // this(); 如果要调用构造器方法，那么必须将this写在构造器中的第一条语句
    }

    public int getNumber(){
        // this();  这种调用构造器的方法仅能在构造器中使用，在其它方法内使用无效，会出现编译错误
        return this.number;
    }
}
