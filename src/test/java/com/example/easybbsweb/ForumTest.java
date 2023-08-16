package com.example.easybbsweb;

import com.example.easybbsweb.controller.response.forum.SecondCommentResp;
import com.example.easybbsweb.domain.entity.ForumArticle;
import com.example.easybbsweb.service.impl.ForumArticleSecondCommentServiceImpl;
import com.example.easybbsweb.utils.TokenUtil;
import jakarta.annotation.Resource;
import org.apache.el.parser.Token;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

@SpringBootTest
public class ForumTest {

    @Resource
    ForumArticleSecondCommentServiceImpl forumArticleSecondCommentService;
//    @Test
//    void func1() throws IOException {
//        ForumArticle forumArticle = new ForumArticle("1","@","ouo","您吃了嘛",new Date().getTime(),0,1);
//        bookRepository.show("d");
//    }
//
public static int lengthOfLongestSubstring(String s) {
    HashMap<Character, Integer> hm = new HashMap<Character, Integer>();
    int cnt=0;//滑动窗口长度
    int maxCnt=0;//最长子串
    int i=0;//滑动窗口右边
    int j=0;//滑动窗口左边
    for(;i<s.length();i++){
        if(!hm.containsKey(s.charAt(i))){
            //如果不存在就将字符和下标存入哈希表
            hm.put(s.charAt(i),i);
            cnt=i-j+1;
            if(cnt>maxCnt){
                maxCnt=cnt;
            }

        }else{
            //如果没有则移动滑动窗口，并重新计算窗口长度
            Integer integer = hm.get(s.charAt(i));
            //左端缓慢移动
            for(;j<=integer;j++){
                hm.remove(s.charAt(j));
            }
            hm.put(s.charAt(i),i);
            cnt=i-j+1;
        }
    }

    return maxCnt;

}

    public static int reverse(int x) {
        if(x==0){
            return 0;
        }
        int temp=0;
        int times=1;
        int sum=0;
        int sign = Integer.signum(x);
        Stack<Integer> stack = new Stack<>();
        while (x!=0){
            temp=x%10;
            stack.push(temp);
            x=x/10;
        }
        //所有数值均进入到栈中，排除栈顶0
        while (stack.peek().intValue()==0){
            stack.pop();
        }
        //开始运算，每运算一次，times*10
        while (!stack.isEmpty()){
            Integer curTop = stack.pop();
            sum+=curTop*times;
            times=times*10;
        }

        return sign==1?sum:-sum;
    }


    public static String convert(String s, int numRows) {
        boolean longDivide=true;//长切割，与短切割交替运行
        ArrayList<StringBuilder> posi = new ArrayList<>();
        for(int m=0;m<numRows;m++){
            posi.add(new StringBuilder());
        }
        int i=0;
        while (i<s.length()){
            if(longDivide){
                //长切割逻辑
                for(int j=0;j<posi.size();j++){
                    posi.get(j).append(s.charAt(i));
                    i++;
                    if(i==s.length()){
                        break;
                    }
                }
                //长切割逻辑之后把longVide置为false
                longDivide=false;

            }else{
                //短切割逻辑
                for(int j=posi.size()-2;j>0;j--){
                    posi.get(j).append(s.charAt(i));
                    i++;
                    if(i==s.length()){
                        break;
                    }
                }
                //短切割逻辑后转为长切割
                longDivide=true;
            }
        }
        StringBuilder nullSb = new StringBuilder();
        for(int k=0;k<posi.size();k++){
            nullSb.append(posi.get(k));
        }
        return nullSb.toString();
    }


    public static void main(String[] args) {
        String test="PAYPALISHIRING";
        String res = convert(test, 3);


    }
}
