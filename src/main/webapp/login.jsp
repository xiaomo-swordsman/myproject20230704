<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2023/7/13
  Time: 9:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>用户登陆</title>
</head>
<body>
<form method="post" action="/login" enctype="multipart/form-data" >
    用户名：<input type="text" name="username"/><br/>
    密码：<input type="password" name="password" /><br/>
    <input type="submit" value="登陆"/>
</form>

</body>
</html>
