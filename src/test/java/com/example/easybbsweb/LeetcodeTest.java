package com.example.easybbsweb;

import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
public class LeetcodeTest {
    public static int myAtoi(String s) {
        //去除两端空格
        String numStr = s.trim();
        //判断符号位
        int sign = 0;//默认0为正数，1为负数
        int startIndex = 0;
        if(numStr.length()==0){
            return 0;
        }
        if (numStr.charAt(0) == '-') {
            sign = 1;
            startIndex = 1;
        }
        long sum = 0;
        for (int i = startIndex; i < numStr.length(); i++) {
            int acc = numStr.charAt(i) - '0';
            if (acc > 9 || acc < 0) {
                //非字符,直接停止解析
                break;
            } else {
                if (sum == 0 && acc == 0) {
                    //数字开头的0字符
                    continue;
                } else {
                    //中间的0字符
                    sum = sum * 10 + acc;
                }
            }
        }
        int resInt=(int) sum;
        if(resInt==sum){
            return sign==0?resInt:-resInt;
        }else{
            //超过惹
            if(sign==0){
                return Integer.MAX_VALUE;
            }else{
                return Integer.MIN_VALUE;
            }
        }
    }

    public static List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        Arrays.sort(nums);
        for(int i=0;i<nums.length;i++){
            if(nums[i]>0){
                return res;
            }
            if(i>0&&nums[i]==nums[i-1]){
                continue;
            }
            int left=i+1;
            int right=nums.length-1;
            while (left<right){
                while (left<right-1&&nums[left]==nums[left+1]){
                    left++;
                }
                while (left<right-1&&nums[right]==nums[right-1]){
                    right--;
                }
                if(nums[left]+nums[right]>-nums[i]){
                    right--;
                }else if(nums[left]+nums[right]<-nums[i]){
                    left++;
                }else if(nums[left]+nums[right]==-nums[i]){
                    ArrayList<Integer> minRes = new ArrayList<>();
                    minRes.add(nums[i]);
                    minRes.add(nums[left]);
                    minRes.add(nums[right]);
                    res.add(minRes);
                    left++;
                    right--;
                }
            }
        }
        return res;

    }

    public  static String  intToRoman(int num) {
        StringBuilder res = new StringBuilder();
        int[] firDivideValue={1000,100,10,1};
        int[] divideValue={1000,500,100,50,10,5,1};
        String[] divideString={"M","D","C","L","X","V","I"};
        int temp=0;
        int more=0;
        while (num!=0){
            for(int i=0;i<firDivideValue.length;i++){
                if(num/firDivideValue[i]==0){
                    num=num%firDivideValue[i];
                    continue;
                }else{
                    //不为0
                    temp=num/firDivideValue[i];
                    if(temp<4){
                        for(int j=0;j<temp;j++){
                            res.append(divideString[2*i]);
                        }
                    }else if(temp==4){
                        //5-1
                        res.append(divideString[2*i]);
                        res.append(divideString[(2*i)-1]);
                    }else if(temp==9){
                        //10-1
                        res.append(divideString[2*i]);
                        res.append(divideString[(2*i)-2]);
                    }else{
                        //5 6 7 8  5+n
                        more=temp-5;
                        res.append(divideString[(2*i)-1]);
                        for(int k=0;k<more;k++){
                            res.append(divideString[2*i]);
                        }
                    }

                    num=num%firDivideValue[i];
                }
            }
        }
        return res.toString();
    }

    public static List<List<Integer>> fourSum(int[] nums, int target) {
        List<List<Integer>> res=new ArrayList<>();
        Arrays.sort(nums);
        int left=-1;
        int right=-1;
        int tempSum=0;
        for(int i=0;i<nums.length;i++){
            if(i>0&&nums[i]==nums[i-1]){
                continue;
            }
            for(int j=i+1;j<nums.length;j++){
                if(j>i+1&&nums[j]==nums[j-1]){
                    continue;
                }
                left=j+1;
                right=nums.length-1;
                while (left<right){
                    tempSum=nums[i]+nums[j]+nums[left]+nums[right];
                    if(tempSum==target){
                        //相等
                        if(left>j+1&&nums[left]==nums[left-1]){
                            //去重
                            left++;
                            continue;
                        }
                        if(right<nums.length-1&&nums[right]==nums[right+1]){
                            right--;
                            continue;
                        }
                        //从这里开始记录进res
                        List<Integer> temp= Arrays.asList(nums[i],nums[j],nums[left],nums[right]);
                        res.add(temp);
                        left++;
                        right--;
                    }else{
                        //不等
                        if(tempSum<target){
                            //left左移
                            left++;
                        }else{
                            right--;
                        }
                    }
                }

            }
        }

        return res;

    }

    public static void main(String[] args) {
        int[] t={1000000000,1000000000,1000000000,1000000000};
        List<List<Integer>> lists = fourSum(t, -294967296);
    }
}
