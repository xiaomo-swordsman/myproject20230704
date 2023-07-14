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
<script src="/js/jquery-3.4.1.min.js"></script>
<%--<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>--%>

<body>
    测试ajax请求提交list集合的数据
</body>
<script language="JavaScript">
        var userList=new Array();
        userList.push({username:"xiaomo",password:"123"});
        userList.push({username:"xiaohong",password:"181"});
        $.ajax({
            type:"POST",
            url:"http://localhost:8080/testList2.do",
            data:JSON.stringify(userList),
            contentType:"application/json;charset=utf-8"
        })

</script>
</html>
