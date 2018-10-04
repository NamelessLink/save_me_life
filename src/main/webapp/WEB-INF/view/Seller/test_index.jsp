<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>This is index</title>
</head>
<body>
<h2>添加菜品</h2>
<form id="addDish" action="/Seller/add_dish" method="get">
    菜名:<input type="text" name="dish_name"><br>
    价格:<input type="text" name="dish_price"><br>
    描述:<input type="text" name="dish_description"><br>
    图片:<input type="text" name="dish_picture"><br>
    类型:<input type="text" name="dish_type"><br>
    餐馆ID:<input type="text" name="r_id"><br>
    <input type="submit" value="添加">
</form>

<h2>删除菜品</h2>
<form id="DeleteDish" action="/Seller/delete_dish" method="get">
    菜品ID:<input type="text" name="dish_id"><br>
    餐馆ID:<input type="text" name="r_id"><br>
    <input type="submit" value="删除">
</form>

<h2>修改菜品</h2>
<form id="modifyDish" action="/Seller/modify_dish" method="get">
    餐馆ID:<input type="text" name="r_id"><br>
    菜品ID:<input type="text" name="dish_id"><br>
    菜名:<input type="text" name="dish_name"><br>
    价格:<input type="text" name="dish_price"><br>
    描述:<input type="text" name="dish_description"><br>
    图片:<input type="text" name="dish_picture"><br>
    类型:<input type="text" name="dish_type"><br>
    <input type="submit" value="修改">
</form>
</body>
</html>
