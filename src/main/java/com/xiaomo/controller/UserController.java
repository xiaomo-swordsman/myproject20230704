package com.xiaomo.controller;


import com.xiaomo.domain.User;
import com.xiaomo.mapper.UserMapper;
import com.xiaomo.pojo.VO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Controller //这个注解表示这个类是一个控制器,可以接收前端的请求
public class UserController {

    @Autowired
    UserMapper userMapper;

    @RequestMapping("/goToPage")
    public String goToPage(){

        // return "/success.jsp"; //这种写法直接返回jsp页面，不经过视图解析器
        return "success";// 这种写法会经过视图解析器，返回jsp页面，但是需要在springmvc.xml中配置视图解析器，配置前缀和后缀，拼接成完整的jsp页面路径
    }

    @RequestMapping("/getUserList")
    @ResponseBody //这个注解表示返回的是json数据，不是jsp页面
    public List<User> getUserList(){
        List<User> userList = userMapper.getUserById(1);
        System.out.println("userList == " + userList);
        return userList;
    }

    @RequestMapping("/getUser")
    @ResponseBody //这个注解表示返回的是json数据，不是jsp页面
    public User getUser(){

        User user = userMapper.getUserByNameString("xiaohong");
        System.out.println("user == " + user);
        return user;
    }

    @RequestMapping("/testList")
    public void testList(VO vo){
        System.out.println("vo == " + vo);
    }

    @RequestMapping("/testList2")
    public void testList2(@RequestBody List<User> userList){
        System.out.println("userList == " + userList);
    }

    @RequestMapping("/testRequestParam")
    @ResponseBody
    public void testRequestParam(@RequestParam(value="name",required = false,defaultValue = "yangteng") String username){
        System.out.println("username = " + username);
//        http://localhost:8080/testRequestParam?name=xiaomo	 username=xiaomo
//        http://localhost:8080/testRequestParam?name2=xiaomo	 username=yangteng
    }


    @RequestMapping("/testPathVariable/{name}")
    @ResponseBody
    public void testPathVariable(@PathVariable(value="name",required = true) String username){
        System.out.println("username = " + username);
        // http://localhost:8080/testPathVariable/xiaomo    username=xiaomo
    }


    @RequestMapping("/testRequestHeader")
    @ResponseBody
    public void testRequestHeader(@RequestHeader(value="User-Agent",required = false) String userAgent){
        System.out.println("userAgent = " + userAgent);
        // http://localhost:8080/testRequestHeader    userAgent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/114.0.0.0 Safari/537.36
    }


    @RequestMapping("/testCookieValue")
    @ResponseBody
    public void testCookieValue(@CookieValue(value="JSESSIONID") String cookieId){
        System.out.println("cookieId = " + cookieId);
        // http://localhost:8080/testCookieValue    cookieId = F53129B77864FCDFC5B84CEF2584258A
    }


    @RequestMapping("/testDateConverter")
    @ResponseBody
    public void testDateConverter(Date date){
        System.out.println("date = " + date);
        // http://localhost:8080/testDateConverter?date=2023-07-12    date = Wed Jul 12 00:00:00 CST 2023
    }

    @RequestMapping("/testFileUpload")
    @ResponseBody
    public void testFileUpload(String operator, MultipartFile uploadFile) throws IOException {

        System.out.println("operator = " + operator);
        if(null != uploadFile){
            // 获取到文件的名称
            String fileName = uploadFile.getOriginalFilename();
            System.out.println("fileName == " + fileName);
            // 将文件保存到指定的地方（可以是服务器)
            uploadFile.transferTo(new File("D:\\test\\" + fileName));
        }
        // http://localhost:8080/testFileUpload.jsp
    }

    @RequestMapping("/login")
    public String login(String username, String password, HttpSession session){
        System.out.println("username == " + username + ",password == " + password);
        User user = userMapper.getUserByUsernameAndPassword(username, password);
        System.out.println("user == " + user);
        if(null != user){
            session.setAttribute("user",user);
            return "redirect:" + session.getAttribute("uri");// 重定向到之前访问的页面
        }
        return "login";
    }

    // 自定义的拦截器的测试
    @RequestMapping("/testInterceptor")
    @ResponseBody
    public void testInterceptor(){

    }

    // 自定义的异常处理的测试
    @RequestMapping("/testException")
    @ResponseBody
    public void testException(){
        int a = 1/0;
    }

}
