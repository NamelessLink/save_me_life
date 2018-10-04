<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>login/register</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
</head>
<body>
<h2>Login</h2>
<form id="login" action="/User/login" method="get">
    用户名:<input type="text" name="account"><br>
    密码:<input type="password" name="pwd"><br>
    <input type="submit" value="登录">
</form>
<h2>Register</h2>
<form id="register" action="/User/register" method="get">
    用户名:<input type="text" name="account"><br>
    密码:<input type="password" name="pwd"><br>
    手机号:<input type="text" name="phone"><br>
    <input type="submit" value="注册">
</form>
<form id="modify" action="/User/modify_view" method="get">
    <input type="submit" value="修改">
</form>
</body>
</html>