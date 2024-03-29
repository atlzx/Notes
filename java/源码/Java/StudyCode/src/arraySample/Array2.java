package arraySample;

public class Array2 {
    public static void main(String[] args) {
        // 二维数组的静态声明方式
        int[][] arr1 = new int[][]{{1, 2}, {3, 4, 5}, {6, 7, 8, 9}};
        // 二维数组的动态声明方式，但仅声明外层元素数量，内层元素数量待定
        int[][] arr2=new int[2][];
        arr2[0]=new int[]{1,2,3,4,5};  // 设定该数组对象第一个元素指向的数组对象的值
        arr2[1]=new int[2];  // 设定该数组对象的第二个元素指向一个元素数量为2的一维数组
        // 二维数组的动态声明方式，一次性声明外层和内层的元素数量
        int[][] arr3=new int[3][4];

        // 二维数组的遍历、获取二维数组长度以及二维数组元素的调用
        for (int i = 0; i < arr1.length; i++) {
            for (int j=0;j<arr1[i].length;j++){
                System.out.println("本次循环输出的数组元素值是:"+arr1[i][j]);
            }
        }

        // int [][] arr4=new byte[4][5]; 该段代码会报错，因为数组不属于基本数据类型，引用数据类型这样做无法进行自动类型提升
    }
}
