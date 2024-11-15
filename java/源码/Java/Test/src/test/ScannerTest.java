package test;

import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;
public class ScannerTest{
    public static void main(String[] args){
        Random random=new Random();
        HashSet set=new HashSet<>();
        int num,prevSize=0,sum=0;
        while(true){
            num=random.nextInt(10);
            set.add(num);
            if(prevSize!=set.size()){prevSize=set.size();sum=sum*10+num;}
            if(prevSize==4){break;}
        }
        System.out.println(sum>1000?"kayak":"wise");
    }
}
