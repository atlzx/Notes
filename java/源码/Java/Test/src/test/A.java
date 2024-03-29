package test;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

public class A{
    public static void main(String[] args){
        Scanner sc=new Scanner(System.in);
        PrintStream out=System.out;
        int m=sc.nextInt();
        int n=sc.nextInt();
        int people,result=0;
        Map<Integer,int[]> map=new HashMap<>();
        Map<Integer,Integer> resultMap=new HashMap<>();
        sc.nextLine();
        for(int i=0;i<n;i++){
            int peopleNumber=sc.nextInt();
            if(i==0){
                resultMap.put(sc.nextInt(),1);
                continue;
            }
            int[] arr=new int[peopleNumber];
            for(int j=0;j<peopleNumber;j++){
                arr[j]=sc.nextInt();
                map.put(i,arr);
            }
            if(sc.hasNext())sc.nextLine();
        }
        boolean isFind=false;
        while(true){
            Iterator<Map.Entry<Integer,int[]>> i1=map.entrySet().iterator();
            int count=0;
            while(i1.hasNext()){
                Map.Entry<Integer,int[]> entry=i1.next();
                for(Integer i:entry.getValue()){
                    if(resultMap.containsKey(i)){
                        isFind=true;
                        count++;
                    }
                }
                if(isFind){
                    result+=entry.getValue().length-count;
                    count=0;
                    map.remove(entry.getKey());
                }
            }
            if(!isFind){
                break;
            }else{
                isFind=false;
            }
        }
        out.println(result);
    }
}
