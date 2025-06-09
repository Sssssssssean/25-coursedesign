package com.course.interceptor;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.Method;

/**
 * @author lixuy
 * Created on 2019-04-10
 */
public class ScoreMethodInterceptor implements HandlerInterceptor {
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler) throws Exception {
        getIdFromHeader();
        System.out.println("before method invoke....");
        try {
            System.out.println("url:"+request.getRequestURI());
            System.out.println("method:"+request.getMethod());
            System.out.println("params:"+request.getParameterMap());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        System.out.println("after method invoke.....");
        return true;
    }
    private void getIdFromHeader() {
        // 获取当前的HttpServletRequest
        System.out.println("get id from header");
        if (RequestContextHolder.getRequestAttributes() == null) {
            UserHolder.setHeader("1");
            return;
        }
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        // 获取请求头参数 todo 模拟从请求头获取id
        String id = request.getHeader("id");
        if (id == null || id.isEmpty()) {
            // 默认id为1
            id = "1";
        }
        UserHolder.setHeader(id);
    }
}
