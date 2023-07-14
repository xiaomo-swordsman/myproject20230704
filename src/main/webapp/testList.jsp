<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2023/7/12
  Time: 13:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <form action="http://localhost:8080/testList.do" method="post">
        姓名:<input type="text" name="userList[0].username"><br>
        密码：<input type="text" name="userList[0].password"><br>
　　　　　姓名:<input type="text" name="userList[1].username"><br>
　　　　　密码：<input type="text" name="userList[1].password"><br>
　　　　　<input type="submit" value="提交"><br>
　　　</form>
</body>
</html>
