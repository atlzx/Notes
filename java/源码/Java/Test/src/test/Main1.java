package test;

import org.junit.Test;

import java.util.*;

public class Main1 {
    public List<List<Integer>> threeSum(int[] nums) {
        Arrays.sort(nums);
        List<Integer> list=null;
        List<List<Integer>> result=new ArrayList<>();
        int left,right;
        int prevValue=Integer.MAX_VALUE;
        for(int i=0;i<=nums.length-1;i++){
            left=i+1;
            right=nums.length-1;
            if(nums[i]==prevValue){
                continue;
            }
            label:while(left<right){
                if(nums[left]+nums[right]>-nums[i]){
                    right--;
                }else if(nums[left]+nums[right]<-nums[i]){
                    left++;
                }else{
                    if((i==0&&nums[i]==0)||(left!=i&&right!=i)){
                        list=new ArrayList<>();
                        list.add(nums[left]);
                        list.add(nums[right]);
                        list.add(nums[i]);
                        result.add(list);
                    }
                    for(int j=left+1;j<=nums.length-1;j++){
                        if(j>=right){
                            break label;
                        }else if(nums[j]!=nums[left]){
                            left=j;
                            break;
                        }
                    }
                }
            }
            prevValue=nums[i];
        }
        return result;
    }
    @Test
    public void test1(){
        System.out.println(threeSum(new int[]{0,0,0}));
    }
}
