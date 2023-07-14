package com.xiaomo.exception;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MyExceptionResolver implements HandlerExceptionResolver {
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception exception) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("error");

        if(exception instanceof MyException){
            modelAndView.addObject("info","自定义异常");
            modelAndView.setViewName("defaultErrorView");
        }else if(exception instanceof ArithmeticException){
            modelAndView.addObject("info","by/zero异常");
            modelAndView.setViewName("ArithmeticException");
        }
        return modelAndView;
    }
}
