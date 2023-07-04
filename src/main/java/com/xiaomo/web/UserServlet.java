package com.xiaomo.web;

import com.xiaomo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserServlet extends HttpServlet {

//    @Autowired
//    private UserService userService;

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp){
        System.out.println("进入了doGet方法...");

        ApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(req.getServletContext());
        UserService userService = applicationContext.getBean(UserService.class);
        userService.save();
    }

}
