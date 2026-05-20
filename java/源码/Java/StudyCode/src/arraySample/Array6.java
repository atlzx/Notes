package arraySample;

public class Array6 {
    public static void main(String[] args) {
        int[] arr = new int[]{-2,11,-4,13,-5,-2 };
        // max变量用来表示所求的最大值,sum用来表示当前子段的和
        int max=0;
        int sum=0;
        for(int i=0;i<arr.length;i++){
            sum+=arr[i];  // 令sum尝试与本次循环对应的数组下标的元素相加
            // 如果sum<=0，那么肯定不是最大值，因为数组的单一元素都有大于0的值
            if(sum<=0){
                sum=0;  // 给sum值重新赋0
            }else if(sum>max){
                // 如果sum不满足上面的条件，那么就判断这个新的sum是否比刚才记录的max值大，如果大，那么更新max的值
                max=sum;
            }
        }
        System.out.println(max);
    }
}
