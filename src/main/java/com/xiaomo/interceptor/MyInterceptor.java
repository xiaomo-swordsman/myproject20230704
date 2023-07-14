package com.xiaomo.interceptor;

import com.xiaomo.domain.User;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpSession;

public class MyInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response, Object handler) throws Exception {
        System.out.println("MyInterceptor preHandle");
        HttpSession session = request.getSession();
        session.setAttribute("uri",request.getRequestURI());// 记录当前请求的uri
        User user = (User)session.getAttribute("user");
        if(null != user){
            // response.sendRedirect(request.getContextPath() + "/success.jsp");
            return true;
        }
        response.sendRedirect(request.getContextPath() + "/login.jsp");
        return false;
    }

    @Override
    public void postHandle(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response, Object handler, org.springframework.web.servlet.ModelAndView modelAndView) throws Exception {
        System.out.println("MyInterceptor postHandle");
    }

    @Override
    public void afterCompletion(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("MyInterceptor afterCompletion");
    }
}
