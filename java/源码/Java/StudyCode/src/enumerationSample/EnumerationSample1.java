package enumerationSample;

public class EnumerationSample1 {
    public static void main(String[] args) {
        System.out.println(EnumerationSample1_1.SPRING.toString());
        System.out.println(EnumerationSample1_1.SUMMER.toString());
        System.out.println(EnumerationSample1_1.AUTUMN.toString());
        System.out.println(EnumerationSample1_1.WINTER.toString());
        System.out.println(EnumerationSample1_1.SPRING.getClass().getSuperclass());  // 被enum关键字修饰的类默认继承Enum类
    }
}

// 使用enum声明的类实际上继承了Enumeration类
enum EnumerationSample1_1{
    // 实例对象需要写在类的最前面,使用 变量名(参数) 的方式声明，可以省略 public static final 前缀，同时省略 new 构造器 后缀，各实例对象彼此使用英文','隔开，最后的变量以';'结尾
    SPRING("春天","春暖花开"),
    SUMMER("夏天","夏日炎炎"),
    AUTUMN("秋天","秋高气爽"),
    WINTER("冬天","白雪皑皑");

    String seasonName,seasonDesc;

    private EnumerationSample1_1(String seasonName,String seasonDesc){
        this.seasonDesc=seasonDesc;
        this.seasonName=seasonName;
    }

    public String toString(){
        return this.seasonName+","+this.seasonDesc;
    }

}
