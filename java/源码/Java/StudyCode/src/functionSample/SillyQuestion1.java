package functionSample;

public class SillyQuestion1 {
    public static void main(String[] args) {
        // 下面演示一道操蛋面试题,题目很简单，请说出下面的输出结果

        int[] arr1 = new int[]{1,2,3,4,5};
        char[] arr2=new char[]{'1','2','3','4','5'};
        String[] arr3=new String[]{"123","456","789","abc","def"};
        System.out.println(arr1); // 输出内存地址
        System.out.println(arr2); // 输出12345。因为这个println方法与它旁边两个println方法不是一个，它们是重载关系，println专门有一个接收char[]类型的对应重载方法
        System.out.println(arr3); // 输出内存地址
    }
}
