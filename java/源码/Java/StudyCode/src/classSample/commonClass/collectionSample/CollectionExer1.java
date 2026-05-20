package classSample.commonClass.collectionSample;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class CollectionExer1 {
    static Object[] arr;
    @Test
    public void test1(){
        Integer[] arr = new Integer[]{1,2,3};
        List list = Arrays.asList(arr);
        System.out.println(list.size());//输出3，原因见主方法内的注释
        System.out.println(list);


        int[] arr1 = new int[]{1,2,3};
        List list1 = Arrays.asList(arr1);
        System.out.println(list1.size());//输出1，原因见主方法内的注释
        System.out.println(list1);
    }

    public static void main(String[] args) {
        /*
            Arrays.asList方法底层会返回ArrayList对象，它接受一个可变参数。
            该方法在得到可变参数后，直接对ArrayList对象承载数据的数组进行赋值（直接将可变参数的地址值赋给该数组对象）
            可变参数在接收元素为引用数据类型的数组时，会将该数组内的元素依次加入可变参数对应的数组中
            而如果是在接收元素为基本数据类型的数组时，会将该数组整体加入可变参数对应的数组中
            下面进行了测试，用来模拟该可变参数的该性质
            如果传入new String[]{"","",""}，那么会展开，将三个空串放入可变参数对应的数组内，因此输出结果是3
            如果传入new int[]{1,2,3}，那么将该数组整体放入可变参数对应的数组内，因此输出结果是1
            如果传入new String[]{"","",""},new String[]{"a","b"}，即传的实参数量大于1，该性质自动失效
            如果传入new String[][]{new String[]{"a","b","c"},new String[]{"a","b"},new String[]{"a"}}，即一个二维数组，它会将该二维数组内的三个数组对象添加进可变参数对应的数组中
         */
        test2(new String[][]{new String[]{"a","b","c"},new String[]{"a","b"},new String[]{"a"}});
        System.out.println(arr.length);
    }

    public static void test2(Object ...obj){
        System.out.println(((String[])obj[1])[1]);
        arr=obj;
    }
}

class Test1{

}

class Test2 extends Test1{

}