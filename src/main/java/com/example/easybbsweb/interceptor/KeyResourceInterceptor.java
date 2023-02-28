//package com.example.easybbsweb.interceptor;
//
//
//import com.example.easybbsweb.utils.TokenUtil;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.web.servlet.HandlerInterceptor;
//
////这个拦截器主要用于监控一些需要大量系统资源的接口，过滤恶意流量
//public class KeyResourceInterceptor implements HandlerInterceptor {
//    public static final String KEY_ATTRIBUTE="oksen"
//
//    //请求进入请求方法之前
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,Object handler)throws Exception{
//        if(request.getMethod().equals("OPTIONS")){
//            return true;
//        }
//
//        Object attribute = request.getSession().getAttribute(KEY_ATTRIBUTE);
//        if(attribute==null){
//            return true;
//        }
//
//        return false;
//
//    }
//
//    //请求的方法处理完后
////    @Override
////    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
////        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
////    }
//
//
//    //页面渲染完后
////    @Override
////    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
////        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
////    }
//}
