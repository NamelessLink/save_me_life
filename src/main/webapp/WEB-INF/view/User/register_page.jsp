<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>register</title>
    <link rel="stylesheet" type="text/css" href="/css/register.css">
</head>
<body>
<div id="title">
    <div id="trademark">
        <img src="/photos/trademark-ico.png">
        <span>饿了吗</span>
    </div>
    <div id="log">
        <span>已有账号？</span>
        <button id="log-btn">登录</button>
    </div>
</div>
<form id="main">
    <label id="nameLabel" for="name">
        用户名
        <input type="text" name="name" id="name" class="normal">
    </label>
    <label id="password1Label" for="password1">
        密码
        <input type="text" name="password1" id="password1" class="normal">
    </label>
    <label id="password2Label" for="password2">
        确认密码
        <input type="text" name="password2" id="password2"  class="normal">
    </label>
    <label id="phoneNumberLabel" for="phoneNumber">
        手机号码
        <input type="text" name="phoneNumber" id="phoneNumber" class="normal">
    </label>
    <input type="button" value="完成注册" id="register-btn">
</form>
<script src="/scripts/dist/register.js"></script>
</body>
</html>
