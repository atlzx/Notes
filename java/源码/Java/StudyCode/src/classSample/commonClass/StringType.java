package classSample.commonClass;

public class StringType {
    public static void main(String[] args) {
        // 字符串的定义
        String str1="";
        String str2="abcd";
        System.out.println(str1+str2+true);  // 打印字符串的拼接

        int int1=12;
        boolean boolean1=true;
        // String str3=boolean1+int1+str1;  该段代码在编译时会发生错误，因为运算顺序是从左到右的，而boolean类型不能参与任何运算，导致编译错误
        String str3=str1+boolean1;

        // int int2=(int)(3+"");  该段代码编译时会发生错误，因为字符串无法通过强制类型转换转换为基本数据类型
        
    }
}
