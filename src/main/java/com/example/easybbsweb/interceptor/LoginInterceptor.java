package com.example.easybbsweb.interceptor;


import com.example.easybbsweb.utils.TokenUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

public class LoginInterceptor implements HandlerInterceptor {

    //请求进入请求方法之前
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,Object handler)throws Exception{
        if(request.getMethod().equals("OPTIONS")){
            return true;
        }
        response.setCharacterEncoding("utf-8");
        String token = request.getHeader("token"); //前端vue将token添加在请求头中

        if(token==null){
            return false;
        }
        else{
            boolean result = TokenUtil.verify(token);
            if(result){
                return true;//放行
            }else{
                response.setCharacterEncoding("UTF-8");
                response.setContentType("application/json; charset=utf-8");
                try{
//                    JSONObject json = new JSONObject();
//                    json.put("msg","illegal token");
//                    json.put("code","50000");
                    String json1="{\"msg\":\"illegal token\",\"code\":\"50000\"}";
                    //发送相应对象
                    response.getWriter().append(json1);

                }catch (Exception e){
                    e.printStackTrace();
                    //告知此时请求失败
                    response.sendError(500);
                    return false;
                }
                return false;
            }
        }

    }

    //请求的方法处理完后
//    @Override
//    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
//        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
//    }


    //页面渲染完后
//    @Override
//    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
//        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
//    }
}
