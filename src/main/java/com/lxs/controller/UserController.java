package com.lxs.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lxs.dao.CustomerMapper;
import com.lxs.dao.RestaurantMapper;
import com.lxs.entity.Customer;
import com.lxs.entity.Dish;
import com.lxs.entity.Restaurant;
import com.lxs.otherentity.*;
import com.lxs.service.UserService;
import com.lxs.util.JsonUtils;
import com.lxs.util.PlanResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/index")
    public String index() {return "/index";}

    @RequestMapping(value = "/User/register_page")
    public String register() {return "/User/register_page";}

    @RequestMapping(value = "/User/index")
    public String user_index(){return "/User/user_index";}

//    @RequestMapping(value = "/User/test_modify_view")
//    public String modify(){return "/User/test_modify_view";}
//
//    @RequestMapping(value = "/User/test_index")
//    public String index() {return "/User/test_index";}
//
//    @RequestMapping(value = "/User/test_login_register")
//    public String login_register() {return "/User/test_login_register";}

    //账号登录
    @RequestMapping(value = "/User/login", method = RequestMethod.GET)
    public void UserLogin(@RequestParam("account") String account, @RequestParam("pwd") String pwd,
                      HttpServletResponse response, HttpServletRequest request, ModelMap model) throws Exception{
        PlanResult ResponseResult = new PlanResult();
        Map<String, Object> resultMap = new HashMap<>();
        response.setContentType("application/json;charset=utf-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        Customer user = null;
        //账号存在且和密码匹配,进入首页
        int vaild_tag = userService.isVaildCustomer(account, pwd);
        if(vaild_tag == 1){
            user = userService.UserLogin(account, pwd);
            resultMap.put("account", user.getAccount());
            resultMap.put("pwd",false);
            resultMap.put("user_id", user.getUserId());
            ResponseResult.setData(resultMap);
            ResponseResult.setMsg("登录成功");
            ResponseResult.setStatus(true);
            String result = JsonUtils.ObjectToJson(ResponseResult);
            out.write(result);
//            response.sendRedirect("/User/index");
//            request.getRequestDispatcher("/User/index").forward(request, response);
            out.close();
        }
        //账号不存在，返回首页
        else {
            if (vaild_tag == 0){
                ResponseResult.setMsg("账号或密码错误");
                ResponseResult.setStatus(false);
                String result = JsonUtils.ObjectToJson(ResponseResult);
                out.write(result);
//                response.sendRedirect("/index");
                out.close();
            }else if (vaild_tag == -1){
                ResponseResult.setMsg("账号或密码为空");
                ResponseResult.setStatus(false);
                String result = JsonUtils.ObjectToJson(ResponseResult);
                out.write(result);
//                response.sendRedirect("/index");
                out.close();
            }
        }
    }

    //账号注册
    @RequestMapping(value = "/User/register", method = RequestMethod.GET)
    public void UserRegister(@RequestParam(value = "account") String account,
                             @RequestParam(value = "pwd") String pwd,
                             @RequestParam(value = "phone") String phone,
                             HttpServletResponse response, HttpServletRequest request,
                             ModelMap model) throws Exception{
        PlanResult ResponseResult = new PlanResult();
        Map<String, Object> resultMap = new HashMap<>();
        response.setContentType("application/json;charset=utf-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        Customer user = null;
        if(userService.isUserExist(account)){
            ResponseResult.setMsg("账号已存在");
            ResponseResult.setStatus(false);
            String result = JsonUtils.ObjectToJson(ResponseResult);
            out.write(result);
//            response.sendRedirect("/User/register_page");
            out.close();
        }else{
            userService.AddUser(account, pwd, phone);
            resultMap.put("account", account);
            resultMap.put("pwd", pwd);
            resultMap.put("phone", phone);
            ResponseResult.setData(resultMap);
            ResponseResult.setMsg("注册成功");
            ResponseResult.setStatus(true);
            String result = JsonUtils.ObjectToJson(ResponseResult);
            out.write(result);
//            response.sendRedirect("/User/index");
            out.close();
        }
    }

    @Autowired
    private CustomerMapper userMapper;

    //个人信息查看
    @RequestMapping(value = "/User/information/{u_id}", method = RequestMethod.GET)
    public void UserInfo(@PathVariable("u_id")String u_id, HttpServletRequest request, HttpServletResponse response, ModelMap model)throws Exception{
        PlanResult ResponseResult = new PlanResult();
        Customer user = userService.UserInfo(u_id);
        Map<String, Object> resultMap = new HashMap<>();
        response.setContentType("application/json;charset=utf-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        resultMap.put("account", user.getAccount());
        resultMap.put("pwd",false);
        if(user.getUserName() == null){
            resultMap.put("name","请输入姓名");
        }else{
            resultMap.put("name",user.getUserName());
        }
        if(user.getAddr() == null){
            resultMap.put("addr","请输入地址");
        }else{
            resultMap.put("addr",user.getAddr());
        }
        resultMap.put("phone", user.getPhone());
        ResponseResult.setData(resultMap);
        ResponseResult.setStatus(true);
        ResponseResult.setMsg("获取个人信息");
        String result = JsonUtils.ObjectToJson(ResponseResult);
        out.write(result);
        out.close();
    }

    //账号个人信息修改
    @RequestMapping(value = "/User/information/{u_id}/modify", method = RequestMethod.GET)
    public void UserModify(@PathVariable("u_id")String u_id, @RequestParam("pwd")String pwd, @RequestParam("name") String name,
                           @RequestParam("phone")String phone, @RequestParam("addr")String addr,
                           HttpServletRequest request, HttpServletResponse response, ModelMap model)throws Exception{
        Customer user = new Customer();
        Map<String, Object> resultMap = new HashMap<>();
        PlanResult ResponseResult = new PlanResult();
        PrintWriter out = response.getWriter();
        if(userService.UserModify(u_id, pwd, name,phone, addr)){
            ResponseResult.setMsg("修改成功");
            ResponseResult.setStatus(true);
            user = userMapper.selectByPrimaryKey(u_id);
            resultMap.put("account", user.getAccount());
            resultMap.put("pwd", false);
            resultMap.put("name", user.getUserName());
            resultMap.put("phone", user.getPhone());
            resultMap.put("addr", user.getAddr());
            ResponseResult.setData(resultMap);
            String result = JsonUtils.ObjectToJson(ResponseResult);
            out.write(result);
//            response.sendRedirect("/User/modify_view");
            out.close();
        }else {
            ResponseResult.setMsg("修改失败");
            ResponseResult.setStatus(true);
            String result = JsonUtils.ObjectToJson(ResponseResult);
            out.write(result);
//            response.sendRedirect("/User/modify_view");
            out.close();
        }
    }

    //首页餐馆列表
    @RequestMapping(value = "/User/restaurant", method = RequestMethod.GET)
    public void Restaurant(HttpServletResponse response, HttpServletRequest request, ModelMap model) throws Exception{
        PlanResult ResponseResult = new PlanResult();
        List<RestaurantName> restaurantName = new LinkedList<RestaurantName>();
        restaurantName = userService.RequestRestaruants();
        response.setContentType("application/json;charset=utf-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        ResponseResult.setData(restaurantName);
        ResponseResult.setStatus(true);
        ResponseResult.setMsg("获取所有餐馆");
//        request.setAttribute  ("AllRestaruants", AllRestaurants);
//        request.getRequestDispatcher("index").forward(request, response);
        String result = JsonUtils.ObjectToJson(ResponseResult);
        out.write(result);
        out.close();
    }

    //首页热门餐馆列表
    @RequestMapping(value = "/User/restaurant/hot", method = RequestMethod.GET)
    public void HotRestaurant(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception{
        PlanResult ResponseResult = new PlanResult();
        List<HotRestaurant> restaurants = new LinkedList<HotRestaurant>();
        restaurants = userService.HotRestaurantList();
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=utf-8");
        PrintWriter out = response.getWriter();
        ResponseResult.setData(restaurants);
        ResponseResult.setStatus(true);
        ResponseResult.setMsg("获取热门餐馆列表");
        String result = JsonUtils.ObjectToJson(ResponseResult);
        out.write(result);
        out.close();
    }

    //获取餐馆详情
    @RequestMapping(value = "/User/restaurant/{r_id}/detail", method = RequestMethod.GET)
    public void RestDetail(@PathVariable(value = "r_id")String r_id, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception{
        PlanResult ResponseResult = new PlanResult();
        response.setContentType("application/json;charset=utf-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
//        RestaruantDetail restaruantDetail = userService.RequestDetail(r_id);
//        Map<String, Object> resultMap = new HashMap<>();
//        resultMap.put("restaurant", restaruantDetail);
        List<DishName> dishNames = new LinkedList<DishName>();
        dishNames = userService.RequestDetail(r_id);
        ResponseResult.setStatus(true);
        ResponseResult.setMsg("餐馆详情");
        ResponseResult.setData(dishNames);
        String result = JsonUtils.ObjectToJson(ResponseResult);
        out.write(result);
        out.close();
    }

    //获取该餐馆热门菜品列表
    @RequestMapping(value = "/User/restaurant/{r_id}/hot", method = RequestMethod.GET)
    public void HotDishList(@PathVariable(value = "r_id")String r_id, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception{
        PlanResult ResponseResult = new PlanResult();
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=utf-8");
        PrintWriter out = response.getWriter();
//        RestaruantDetail restaruantDetail = userService.RequestDetail(r_id);
//        Map<String, Object> resultMap = new HashMap<>();
//        resultMap.put("restaurant", restaruantDetail);
        List<HotDish> hotDishes = new LinkedList<HotDish>();
        hotDishes = userService.HotDishList(r_id);
        ResponseResult.setStatus(true);
        ResponseResult.setMsg("获取热门菜品列表");
        ResponseResult.setData(hotDishes);
        String result = JsonUtils.ObjectToJson(ResponseResult);
        out.write(result);
        out.close();
    }

    //获取菜品详情
    @RequestMapping(value = "/User/restaurant/{r_id}/{dish_id}/detail")
    public void DishDetail(@PathVariable(value = "r_id")String r_id, @PathVariable(value = "dish_id")String dish_id,
                           HttpServletRequest request, HttpServletResponse response,
                           ModelMap model)throws Exception{
        PlanResult ResponseResult = new PlanResult();
        Dish dish = userService.DishDetail(dish_id);
        response.setContentType("application/json;charset=utf-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        ResponseResult.setStatus(true);
        ResponseResult.setMsg("菜品详情");
        ResponseResult.setData(dish);
        String result = JsonUtils.ObjectToJson(ResponseResult);
        out.write(result);
        out.close();
    }

    //用户提交订单
    @RequestMapping(value = "/User/add_order", method = RequestMethod.GET)
    public void SubmitOrder(@RequestParam("u_id")String u_id, @RequestParam("r_id")String r_id, @RequestParam("dish_id")String dish_id,
                            HttpServletRequest request, HttpServletResponse response, ModelMap model) throws IOException {
        PlanResult ResponseResult = new PlanResult();
        response.setContentType("application/json;charset=utf-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        if(userService.SubmitOrder(u_id, r_id, dish_id)){
            ResponseResult.setStatus(true);
            ResponseResult.setMsg("提交订单");
        }else{
            ResponseResult.setStatus(false);
            ResponseResult.setMsg("请完成姓名和地址的输入");
        }
        String result = JsonUtils.ObjectToJson(ResponseResult);
        out.write(result);
        out.close();
    }

    //用户查看未完成订单状态
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @RequestMapping(value = "/User/order/{u_id}", method = RequestMethod.GET)
    public void CheckOrder(@PathVariable("u_id")String u_id, HttpServletRequest request, HttpServletResponse response,
                           ModelMap model)throws Exception{
        List<OrderDetail> orders = new LinkedList<OrderDetail>();
        PlanResult ResponseResult = new PlanResult();
        response.setContentType("application/json;utf-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        orders = userService.CheckOrder(u_id);
        ResponseResult.setStatus(true);
        ResponseResult.setMsg("订单详情");
        ResponseResult.setData(orders);
        String result = JsonUtils.ObjectToJson(ResponseResult);
        out.write(result);
        out.close();
    }

    //用户完成订单
    @RequestMapping(value = "/User/order/finish/{order_id}", method = RequestMethod.GET)
    public void FinishOrder(@PathVariable("order_id")String order_id, HttpServletRequest request, HttpServletResponse response, ModelMap model)throws Exception{
        PlanResult ResponseResult = new PlanResult();
        response.setContentType("application/json;charset=utf-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        userService.FinishOrder(order_id);
        ResponseResult.setStatus(true);
        ResponseResult.setMsg("完成订单");
        String result = JsonUtils.ObjectToJson(ResponseResult);
        out.write(result);
        //跳转到查看订单
        out.close();
    }
}
