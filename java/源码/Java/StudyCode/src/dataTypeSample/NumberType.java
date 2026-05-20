package dataTypeSample;

public class NumberType {
    public static void main(String[] args) {
        byte b1 = 127;  // byte类型变量占用1个字节，8bit
        // byte b2=128;  超出类型大小限制，编译会出错
        short s1 = 1152; // short类型变量占用2个字节，16bit
        int number = 123; // int类型变量占用4个字节，32bit，整型字面量默认是int类型的
        long long1 = 12L;  // 使用long声明变量，需要在数字之后加上“L”或“l”,long类型变量占用8个字节，64bit，用于表示绝对值比较大的整数
        float f1=15.2F;  // 使用float声明变量，需要在数字后加上“F”或“f”，这是因为浮点数默认类型为double。float类型变量占用4个字节，表示范围比long类型大，但精度不高
        double d1=12.2;  // double类型占用8个字节，浮点数字面量默认是double类型的
        System.out.println(0.1+0.2);  // 无论是float还是double.都有精度问题，如果需要进行高精度运算，需要使用到BigDecimal类
    }
}
