package com.example.easybbsweb.config;


import com.example.easybbsweb.filter.MyCorsFilter;
import com.example.easybbsweb.interceptor.LoginInterceptor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AuthWebMvcConfig implements WebMvcConfigurer {
    /**
     * 开启跨域
     */

//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        // 设置允许跨域的路由
//        registry.addMapping("/**")
//                // 设置允许跨域请求的域名
//                .allowedOriginPatterns("*")
////                 设置允许的方法
//                .allowedMethods("GET","HEAD","POST","PUT","DELETE","OPTIONS")
//                // 是否允许携带cookie参数
//                .allowCredentials(true)
//                // 设置允许的方法
//                .allowedMethods("*")
//                // 跨域允许时间
//                .maxAge(4600);
//    }

    @Bean
    public FilterRegistrationBean CorsFilter() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        //注入过滤器
        registrationBean.setFilter(new MyCorsFilter());
        //过滤器名称
        registrationBean.setName("MyCorsFilter");
        //拦截规则
        registrationBean.addUrlPatterns("/*");
        //过滤器顺序
        registrationBean.setOrder(FilterRegistrationBean.HIGHEST_PRECEDENCE);

        return registrationBean;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/**")//让拦截器进行token验证
                .addPathPatterns("/universityVerify/**")
                .excludePathPatterns("/user/**")

                //这个路径下直接放行不需要拦截器token验证
                .excludePathPatterns("/picture/**")
                .excludePathPatterns("/public/**");//放行动态资源
    }
}
