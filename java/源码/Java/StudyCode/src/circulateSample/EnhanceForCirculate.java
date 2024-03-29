package circulateSample;

import java.util.ArrayList;
import java.util.List;

public class EnhanceForCirculate {
    public static void main(String[] args) {
        List list=new ArrayList();
        list.add(1);
        list.add("bbb");
        // 使用增强for循环遍历集合框架对象
        for(Object obj:list){
            System.out.println(obj);
        }
    }
}
