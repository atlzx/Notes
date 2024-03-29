package arraySample;

public class Array3 {
    public static void main(String[] args) {
        // 实现数组的反转，即使数组倒序

        // 方法一:
        int[] arr = {1, 2, 3, 4, 5};
        for (int i = 0; i < arr.length / 2; i++) {
            arr[i] += arr[arr.length - i - 1];
            arr[arr.length - i - 1] = arr[i] - arr[arr.length - i - 1];
            arr[i] = arr[i] - arr[arr.length - i - 1];
        }

        // 输出数组
        System.out.println("方法一输出");
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i]);
        }
        System.out.println();

        // 方法二:更牛逼的循环
        arr = new int[]{1, 2, 3, 4, 5};
        // 该处循环的条件不能是小于等于，因为元素数量为奇数时，等于情况会成立，此时如果使用这种交换变量值的方式会导致arr[i]和arr[j]所读取到的值是一样的,因为i和j就是一样的
        // 这会导致最后该数组中间的元素值变成0
        for (int i = 0, j = arr.length - 1; i < j; i++, j--) {
            arr[i] += arr[j];
            arr[j] = arr[i] - arr[j];
            arr[i] = arr[i] - arr[j];
        }
        // 输出数组
        System.out.println("方法二输出");
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i]);
        }
        System.out.println();

        // 方法三:简单粗暴而最容易想到的方法
        // 该方法既牺牲了时间，也牺牲了空间，非常不推荐
        arr=new int[]{1,2,3,4,5};
        int[] newArray=new int[arr.length];
        for(int i=arr.length-1;i>=0;i--){
            newArray[arr.length-i-1]=arr[i];
        }
        System.out.println("方法三输出:");
        for (int i = 0; i < newArray.length; i++) {
            System.out.print(newArray[i]);
        }
    }
}
