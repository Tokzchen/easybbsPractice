package com.example.easybbsweb;

import java.util.ArrayList;
import java.util.List;

class Solution22 {
    private static List<String> res=new ArrayList<>();

    public List<String> generateParenthesis(int n) {
        StringBuilder stringBuilder = new StringBuilder();
        for(int i=0;i<n;i++){
            stringBuilder.append('(');
        }
        Backtracking(n,0,stringBuilder);
        return res;
    }

    protected static void Backtracking(int n, int startIndex,StringBuilder path){
        //递归中止条件
        if(path.length()==2*n){
            //收集结果
            res.add(path.toString());
            return;
        }
        //单层递归条件
        for(int i=startIndex;i<path.length();i++){
            path.insert(i+1,')');
            //剪枝操作
            if(checkIsLegal(path)){
                //合法继续往下递归
                Backtracking(n,i+1,path);
                //回溯操作
                path.deleteCharAt(i+1);
            }else{
                path.deleteCharAt(i+1);
            }
        }
    }

    //检查一个括号字符串是否合法,从左括号为cnt+1,右括号cnt-1,一旦cnt为负则违法
    protected static boolean checkIsLegal(StringBuilder path){
        int cnt=0;
        for(int i=0;i<path.length();i++){
            if(path.charAt(i)=='('){
                cnt++;
            }else{
                cnt--;
            }
            if(cnt<0){
                return false;
            }
        }
        return true;

    }

    public static void main(String[] args) {
        List<String> list = new Solution22().generateParenthesis(1);
        System.out.println(list);
    }
}