package exceptionSample;

public class ExceptionSample2 {
    public static void main(String[] args) {
        test1("aaa");
        test2("12");
        int result=test3("a");
        System.out.println(result); // 此处输出0
        int number=test4(10);
        System.out.println(number);  // 此处输出10，原因见下
    }
    public static int test1(String str){
        try{
            Integer.parseInt(str);
            return 1;
        }catch(NumberFormatException e){
            return -1;
        }finally{
            System.out.println("test结束");  // 即使有return，finally的代码也会在return之前执行
        }
    }

    public static int test2(String str) {
        try {
            Integer.parseInt(str);
            return 1;
        } catch (NumberFormatException e) {
            return -1;
        } finally {
            System.out.println("test结束");
        }
    }

    public static int test3(String str) {
        try {
            Integer.parseInt(str);
            return 1;
        } catch (NumberFormatException e) {
            return -1;
        } finally {
            System.out.println("test结束");
            return 0;
        }
    }

    public static int test4(int num) {
        try {
            return num;
        } catch (NumberFormatException e) {
            return num--;
        } finally {
            System.out.println("test结束");
//            return ++num;  如果执行该语句，那么返回的是11
            ++num;
            // 返回10的原因:++num时，先将num自增前的值放在方法区的一块内存中，然后执行自增，但执行完该操作return时，程序读取的是方法区内存放的num自增前的值，因此为10
        }
    }
}
