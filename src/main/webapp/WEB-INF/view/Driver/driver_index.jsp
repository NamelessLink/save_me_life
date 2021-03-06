<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>骑手</title>
    <link rel="stylesheet" type="text/css" href="/css/seller.css">
</head>
<body>
<div id="app">
    <div id="trademark">
        <img src="/photos/trademark-ico.png">
        <span>饿了吗</span>
    </div>
    <div id="main">
        <div id="navigation">
            <button data-id="homePage-nav">管理合作</button>
            <button data-id="order-nav">派送订单</button>
        </div>
        <div id="homePage" class="appear">
            <div id="restaurantPage" class="appear">
            </div>
            <button id="addDish" class="plus">加菜</button>
        </div>
        <div id="orderPage" class="disappear">
            <div id="orderPageContainer">
            </div>
        </div>

    </div>
    <script src="/scripts/dist/driver.js"></script>
</div>
</body>
</html>