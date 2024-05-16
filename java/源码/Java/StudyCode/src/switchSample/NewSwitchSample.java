package switchSample;

import org.junit.Test;

public class NewSwitchSample {
//    @Test
//    public void test1(){
//        NewSwitchSample1 a=new NewSwitchSample3();
//        int number=switch(a){
//            // 如果变量a是对应类型的，那么将a强转为该类型的对象赋值给a1
//            case NewSwitchSample3 a1 ->{
//                yield 1;
//            }
//            case NewSwitchSample2 a1 ->{
//                yield 2;
//            }
//            default->{
//                yield 3;
//            }
//        };
//        System.out.println(number);
//    }
    @Test
    public void test2(){
        String a="abcd";
        int b=switch(a){
            case "bcda":yield 1;
            case "cbda":yield 2;
            case "abcd":yield 3;
            default:yield 4;
        };
        System.out.println(b);
    }
}

class NewSwitchSample2 extends NewSwitchSample1{

}

class NewSwitchSample3 extends NewSwitchSample1{

}

class NewSwitchSample1{

}
