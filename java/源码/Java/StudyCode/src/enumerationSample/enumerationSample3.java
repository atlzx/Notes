package enumerationSample;

public class enumerationSample3 {
    public static void main(String[] args) {
        System.out.println(EnumerationSample3_1.info);
    }
}

// 早期声明枚举类的方法
class EnumerationSample3_1{
    public final static EnumerationSample3_1 info=new EnumerationSample3_1("lzx",20,"2024-2-3-17:17");
    private final String name;
    private final int age;
    private final String date;
    private EnumerationSample3_1(String name,int age,String date){
        this.name=name;
        this.age=age;
        this.date=date;
        Boolean.valueOf("aaa");
    }
    public String toString(){
        return "姓名:"+this.name+",年龄:"+this.age+",创建时间:"+this.date;
    }
}