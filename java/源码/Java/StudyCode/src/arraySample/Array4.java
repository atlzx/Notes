package arraySample;

public class Array4 {
    public static void main(String[] args) {

        // 数组的扩容与缩容

        // 数组的扩容
        // 由于数组的长度一旦确定便不可变，因此数组的扩容只能是重新申请更大的内存，使新的数组能容纳的元素数量大于之前的数组，一般都是之前数组容量的的两倍
        int[] arr1 = new int[]{1, 2, 3, 4, 5, 6, 7};
        // 首先定义一个新数组,该新数组的元素数是待扩容数组的两倍
        int[] arr2 = new int[arr1.length * 2];  // 或者写成 int[]arr2 = new int[arr.length<<1];
        // 遍历原数组，将原数组的每一个元素值都赋给新数组对应下标的元素值
        for (int i = 0; i < arr1.length; i++) {
            arr2[i] = arr1[i];
        }
        // 最后使原数组指向新数组
        arr1 = arr2;
        System.out.println("输出扩容后的数组");
        for (int i = 0; i < arr1.length; i++) {
            System.out.print(arr1[i]);
        }
        System.out.println();

        // 数组的缩容
        // 数组的缩容一般分两种:第一种是将被删除元素值后面的元素整体向前挪动，第二种是重新创建一个数组，里面的内容不包含删除的元素值
        // 下面先演示第一种缩容方式，一般缩容都使用该方式实现

        int[] arr3 = new int[]{1, 2, 3, 4, 5, 6, 7};
        int deleteIndex = 4;  // 假设要删除的元素是索引为4的元素
        for (int i = deleteIndex; i < arr3.length - 1; i++) {
            // 从被删除的元素开始，整体向前移动
            arr3[i] = arr3[i + 1];
        }
        arr3[arr3.length - 1] = 0;  // 最后将最后的元素值置为默认初始值
        System.out.println("输出缩容后的数组(方法一):");
        for (int i = 0; i < arr3.length; i++) {
            System.out.print(arr3[i]);
        }
        System.out.println();


        // 下面演示第二种缩容方式:
        arr3 = new int[]{1, 2, 3, 4, 5, 6, 7};
        // 首先声明新的数组，该数组长度为待缩容数组长度-1，因为删除了一个元素，实际情况取决于删除的元素有多少个
        int[] arr4 = new int[arr3.length - 1];
        // 循环给arr4赋值
        for (int i = 0; i < deleteIndex; i++) {
            arr4[i] = arr3[i];
        }
        for (int i = deleteIndex; i < arr3.length - 1; i++) {
            arr4[i] = arr3[i + 1];
        }
        System.out.println("输出缩容后的数组(方法二):");
        for (int i = 0; i < arr4.length; i++) {
            System.out.print(arr4[i]);
        }

    }
}
