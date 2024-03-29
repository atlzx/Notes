package keywordSample;

public class SuperKeywords2 extends SuperKeywords1{
    String id="子类的id";
    public SuperKeywords2(){

    }

    public static void main(String[] args) {
        SuperKeywords2 superKeywords2=new SuperKeywords2();
        superKeywords2.show1();
    }
    public SuperKeywords2(String id){
        super();
        this.id=id;
    }

    public void say(){
        System.out.println("子类的方法");
    }

    public void show1(){
        System.out.println(super.id);  // 使用super调用父类的属性
        System.out.println(id);
        super.say();  // 使用super调用被重写的父类的方法
        say();
    }
}
