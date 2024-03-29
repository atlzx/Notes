package dataTypeSample;

public class CharType {

    public static void main(String[] args) {
        // char c1='';  这样写编译不通过

        // 写法一:直接写单引号，里面加一个字符
        char c2 = 'a';
        char c3 = '2';
        char c4 = '?';
        // 写法二:使用Unicode字符值表示字符常量
        char c5 = '\u0036';

        // 写法三:使用转义字符\表示一些特殊字符
        char c6 = '\n'; // 使用转义字符表示字符

        // 写法四:使用数值进行赋值
        char c7 = 48;  // 直接使用数字赋值,该字符为ASCII码的数值对应的字符

        System.out.println("单字符:"+c2+"、"+c3+"、"+c4);
        System.out.println("Unicode字符:" + c5);
        System.out.println("转义字符:" + c6);
        System.out.println("ASCII数值对应的字符:"+c7);
    }
}
