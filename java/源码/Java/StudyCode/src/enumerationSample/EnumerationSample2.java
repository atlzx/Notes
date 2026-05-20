package enumerationSample;

public class EnumerationSample2 {
    public static void main(String[] args) {

    }
}

// 改进单例模式(饿汉式)
class Enumeration2_1{
    private Enumeration2_1(){
        
    }
    public final static Enumeration2_1 test=new Enumeration2_1();
}
