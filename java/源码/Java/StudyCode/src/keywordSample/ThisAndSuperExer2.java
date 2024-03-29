package keywordSample;

public class ThisAndSuperExer2{
    public static void main(String[] args) {
        // 观察下面的代码，说明输出结果
        Father f = new Father();
        Son s = new Son();
        System.out.println(f.getInfo());// 重写getInfo前:atguigu，重写后:atguigu
        System.out.println(s.getInfo()); // 重写getInfo前:atguigu，重写后:尚硅谷
        s.test(); // 重写getInfo前:atguigu、atguigu，重写后:尚硅谷、atguigu
        System.out.println("-----------------");
        s.setInfo("大硅谷");  // 每个子类对象都有它自己独有的父类对象，更改一个子类对象的父类对象属性值对另外的父类对象不会有影响
        System.out.println(f.getInfo()); // 重写getInfo前:atguigu，重写后:atguigu
        System.out.println(s.getInfo()); // 重写getInfo前:大硅谷，重写后:尚硅谷
        s.test();  // 重写getInfo前:大硅谷、大硅谷，重写后:尚硅谷、大硅谷
    }
}
class Father{
    private String info = "atguigu";
    public void setInfo(String info){
        this.info = info;
    }
    public String getInfo(){
        return info;
    }
}
class Son extends Father{
    private String info = "尚硅谷";
    public void test(){
        System.out.println(this.getInfo());
        System.out.println(super.getInfo());
    }

    public String getInfo(){
        return info;
    }
}
