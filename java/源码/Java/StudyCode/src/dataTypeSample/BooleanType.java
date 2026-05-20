package dataTypeSample;

public class BooleanType {
    public static void main(String[] args) {
        /*
            Boolean类型只有两个值，true和false，
            它们无法被转换为1和0，但JVM进行代码执行时，会将Boolean类型转换为int类型再进行运算
            它们占用4个字节
         */
        boolean b1 = true;
        boolean b2 = false;
    }
}
