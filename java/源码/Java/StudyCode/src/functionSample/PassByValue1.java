package functionSample;

public class PassByValue1 {
    public static void main(String[] args) {
        int a=10;
        int b=20;
        // 结果是并没有发生变量值的置换，因为基本数据类型在进行值传递时直接传递数据值，而真正进行置换的是调用方法中的局部变量，这些局部变量在方法执行完毕后就随着方法出栈被销毁了
        swapBaseData(a,b);
        System.out.println("a="+a);
        System.out.println("b="+b);


        int[] arr=new int[]{10,20,30,40,50};
        // 这会发生数组元素的置换，引用数据类型值传递时，传递的是地址值，这就使得方法内的局部变量通过地址也指向该数组对象实体，在方法内执行的置换操作也会作用于数组对象
        swap(arr,2,4);
        for (int i = 0; i < arr.length; i++) {
            System.out.println("遍历数组输出结果"+arr[i]);
        }
    }

    public static void swapBaseData(int a,int b){
        int temp=a;
        a=b;
        b=temp;
    }
    public static void swap(int[] arr,int index1,int index2){
        int temp=arr[index1];
        arr[index1]=arr[index2];
        arr[index2]=temp;
    }
}
