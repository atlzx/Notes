package typeConversionSample;

public class AutoTypeElevation {
    public static void main(String[] args) {
        /*
            特殊情况
            byte byte1=1;
            short short1=10;
            char c1='a';
            short short2=byte1+short1;
        */

        // 一般情况
        int int1 = 12;
        int int2='a';  // 将char类型的变量存储的字符对应的编码数值转换为int类型赋值给int变量
        float float1 = 12.2f;
        double double1 = 13.5;
        double double2 = int1 + float1 + double1;
        System.out.println("运算结果为:" + double2);
        System.out.println("int接收char类型的结果:"+int2);


        byte byte1 = 12;
        int int3=byte1+13;  // 字面量13被Java默认识别为int类型变量，需要使用int类型或更高的表示范围的类型来接收

        float float2=11.2f;
        double double3=float2+10.2;  // 字面量10.2被Java默认识别为double类型变量，需要使用double类型或更高的表示范围的类型来接收
    }
}
