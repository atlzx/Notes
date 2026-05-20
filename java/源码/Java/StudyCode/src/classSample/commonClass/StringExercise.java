package classSample.commonClass;

public class StringExercise {
    public static void main(String[] args) {
        // 下面是一些字符串小练习,主要考察字符串拼接和char类型的运算
        // String str1 = 4;  编译失败
        String str2 = 3.5f + "";               //判断str2对错：3.5
        System.out.println(str2);              //输出：3.5
        System.out .println(3+4+"Hello!");     //输出：7Hello!
        System.out.println("Hello!"+3+4);      //输出：Hello!34
        System.out.println('a'+1+"Hello!");    //输出：98Hello
        System.out.println("Hello"+'a'+1);     //输出：Helloa1


        System.out.println("*    *");				//输出：*    *
        System.out.println("*\t*");					//输出：*  *
        System.out.println("*" + "\t" + "*");		//输出：*  *
        System.out.println('*' + "\t" + "*");		//输出：*  *
        System.out.println('*' + '\t' + "*");		//输出：51*
        System.out.println('*' + "\t" + '*');		//输出：*  *
        System.out.println("*" + '\t' + '*');		//输出：*  *
        System.out.println('*' + '\t' + '*');		//输出：93
    }
}
