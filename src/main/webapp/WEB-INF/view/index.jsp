<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>饿了吗</title>
    <link href="/css/log.css" type="text/css" rel="stylesheet">
</head>
<body>
<div id="main">
    <div id="mask1"></div>
    <div id="title">
        <div id="container-title">
            <div id="name-title">
                <span>[</span>
                <span>外卖点餐网站</span>
                <span>]</span>
            </div>
            <img id="tradmark2-title" src="/photos/trademark-ico.png">
            <span id="tradmark1-title">饿了吗</span>
            <br>
            <span id="info-title">我们专注外卖百余年</span>
        </div>
    </div>
    <form id="logIn">
        <select name="userType" class="input inputBorder">
            <option value="-1" selected="selected">请选择用户类型</option>
            <option value="0">买家</option>
            <option value="1">卖家</option>
            <option value="2">骑手</option>
        </select>
        <input type="text" name="userName" class="input inputBorder" placeholder="请输入用户名">
        <input type="number" name="password" class="input inputBorder" placeholder="请输入密码">
        <input type="button" id="confirmBtn" class="inputBorder" value="确认登录">
    </form>
    <a id="new-user" href="/User/register_page">新用户注册</a>
</div>
<script src="/scripts/dist/index.js">
</script>
</body>
</html>