package exceptionSample;

import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Scanner;

public class CommonExceptionSample {
    @Test
    public void test1(){
        // ArrayIndexOutOfBoundsException
        int[] arr = new int[10];
        System.out.println(arr[10]);
    }

    @Test
    public void test2(){
        // NullPointerException
        String str = "hello";
        str = null;
        System.out.println(str.toString());

        int[] arr = new int[10];
        arr = null;
        System.out.println(arr[0]);

        int[][] arr1 = new int[10][];
        System.out.println(arr1[0][0]);
    }

    @Test
    public void test3(){
        // ClassCastException
        Object obj = new String();
        String str = (String) obj;

        Date date = (Date) obj;
    }

    @Test
    public void test4(){
        // NumberFormatException
        String str = "123";
        str = "abc";
        int i = Integer.parseInt(str);
        System.out.println(i);
    }

    @Test
    public void test5(){
        // 输入非int类型值会导致InputMismatchException
        Scanner scanner = new Scanner(System.in);
        int num = scanner.nextInt();
        System.out.println(num);
    }

    @Test
    public void test6(){
        // ArithmeticException
        int num = 10;
        System.out.println(num / 0);
    }

    @Test
    public void test7() throws ClassNotFoundException{
        // ClassNotFoundException
        Class clz = Class.forName("java.lang.String");
    }

    @Test
    public void test8() throws IOException {
        // FileNotFoundException 和 IOException,但前者是后者的父类
        // 编译时异常必须进行处理
        File file = new File("D:\\hello.txt");

        FileInputStream fis = new FileInputStream(file); //可能报FileNotFoundException

        int data = fis.read(); //可能报IOException
        while(data != -1){
            System.out.print((char)data);
            data = fis.read(); //可能报IOException
        }

        fis.close(); //可能报IOException
    }
}
