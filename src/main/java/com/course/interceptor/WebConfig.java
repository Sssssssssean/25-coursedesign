package com.course.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private GenInterceptor genInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(genInterceptor)
                .addPathPatterns("/**")
                // 排除积分测试观察接口和账户相关（如登录）
                .excludePathPatterns("/current/user/**", "/error", "/account/**");
    }
}
