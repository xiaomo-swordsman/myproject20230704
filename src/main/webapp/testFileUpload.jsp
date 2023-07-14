<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2023/7/12
  Time: 17:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <form action="http://localhost:8080/testFileUpload" method="post" enctype="multipart/form-data">
        操作者:<input type="text" name="operator"><br>
        文件：<input type="file" name="uploadFile"><br>
        <input type="submit" value="提交">
    </form>
</body>
</html>
