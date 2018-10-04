<%--
  Created by IntelliJ IDEA.
  User: MrL
  Date: 2018/9/12
  Time: 9:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Modify</title>
</head>
<body>
<h2>Modify</h2>
<form id="Modify" action="/User/information/${u_id}" method="get">
    用户ID:<input type="text" name="u_id" value=${u_id}><br>
    密码:<input type="password" name="pwd"><br>
    手机号:<input type="text" name="phone"><br>
    地址:<input type="text" name="addr"><br>
    <input type="submit" value="确认">
</form>

</body>
</html>
