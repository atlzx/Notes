package typeConversionSample;

public class CastTypeConversion {
    public static void main(String[] args) {
        int int1 = 12;
        byte byte1 = (byte) int1;  // 想要将高表示范围的类型转换为低表示范围的类型，需要使用强制类型转换

        // 强制类型转换可能会出现精度损失

        int int2 = 128;
        byte byte2 = (byte) int2;
        System.out.println("类型转换后的byte2:" + byte2);  // 输出-128

        double double1=12.9;
        int int3=(int)double1;
        System.out.println(int3);  // 强制类型转换将浮点数转为整数时，遵循的是取整原则，而不是四舍五入原则
    }
}
