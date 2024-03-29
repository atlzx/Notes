package test;

import org.junit.Test;

import java.util.Scanner;

public class HelloWorld{
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        String a=sc.next();
        System.out.println(a);
//        int len1=sc.nextInt(),len2=sc.nextInt(),num=sc.nextInt();
//        sc.nextLine();
//        String str1=sc.nextLine(),str2=sc.nextLine();
//        int result;
//        int leftMin,leftMax,rightMin,rightMax;
//        boolean hasResult=false;
//        for(int i=1;i<=num;i++){
//            leftMin=sc.nextInt()-1;
//            leftMax=sc.nextInt();
//            rightMin=sc.nextInt()-1;
//            rightMax=sc.nextInt();
//            for(int j=leftMin,k=rightMin;j<leftMax;j++,k++){
//                result=str1.charAt(j)-str2.charAt(k);
//                if(result>0){
//                    System.out.println('>');
//                    hasResult=true;
//                    break;
//                }else if(result<0){
//                    System.out.println('<');
//                    hasResult=true;
//                    break;
//                }
//            }
//            if(!hasResult){
//                System.out.println('=');
//            }
//            if(sc.hasNext())sc.nextLine();
//        }
    }

    @Test
    public void test1(){
        Scanner sc=new Scanner(System.in);
        byte a=(byte)(sc.nextInt());
        System.out.println(a);
    }
}