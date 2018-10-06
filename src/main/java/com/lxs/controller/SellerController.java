package com.lxs.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lxs.dao.DishMapper;
import com.lxs.entity.Dish;
import com.lxs.entity.MenuKey;
import com.lxs.entity.Order;
import com.lxs.entity.Seller;
import com.lxs.otherentity.DishName;
import com.lxs.otherentity.DriverName;
import com.lxs.otherentity.OrderDetail;
import com.lxs.otherentity.RestaurantName;
import com.lxs.service.SellerService;
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
import java.io.PrintWriter;
import java.util.*;


@Controller
public class SellerController {

    @Autowired
    private SellerService sellerService;

    @RequestMapping(value = "/Seller/index")
    public String user_index(){return "/Seller/seller_index";}

//    @RequestMapping(value = "/Seller/index")
//    public String index() { return "/Seller/test_index"; }
//
//    @RequestMapping(value = "/Seller/login_view")
//    public String loginpage() { return "/Seller/test_login_view"; }

    //商家登录
    @RequestMapping(value = "/Seller/login",  method = RequestMethod.GET)
    public void SellerLogin(@RequestParam("account")String account, @RequestParam("pwd")String pwd,
                            HttpServletRequest request, HttpServletResponse response, ModelMap model)throws Exception{
        PlanResult ResponseResult = new PlanResult();
        Map<String, Object> resultMap = new HashMap<>();
        response.setContentType("application/json;charset=utf-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        Seller seller = null;
        //账号存在且和密码匹配,进入首页
        int vaild_tag = sellerService.isVaildSeller(account, pwd);
        if(vaild_tag == 1){
            seller = sellerService.sellerLogin(account, pwd);
            resultMap.put("account", seller.getAccount());
            resultMap.put("password",false);
            resultMap.put("user_id", seller.getSellerId());
            resultMap.put("r_id", seller.getrId());
            ResponseResult.setData(resultMap);
            ResponseResult.setMsg("登录成功");
            ResponseResult.setStatus(true);
            String result = JsonUtils.ObjectToJson(ResponseResult);
            out.write(result);
//            request.getRequestDispatcher("/Seller/index").forward(request, response);
            out.close();
        }
        //账号不存在，返回首页
        else {
            if (vaild_tag == 0){
                ResponseResult.setMsg("账号或密码错误");
                ResponseResult.setStatus(false);
                String result = JsonUtils.ObjectToJson(ResponseResult);
                out.write(result);
//                response.sendRedirect("/Seller/login_view");
                out.close();
            }else if (vaild_tag == -1){
                ResponseResult.setMsg("账号或密码为空");
                ResponseResult.setStatus(false);
                String result = JsonUtils.ObjectToJson(ResponseResult);
                out.write(result);
//                response.sendRedirect("/Seller/login_view");
                out.close();
            }
        }
    }

    //商家获取菜单
    @RequestMapping(value = "/Seller/{r_id}/menu")
    public void Menu(@PathVariable("r_id")String r_id, HttpServletRequest request, HttpServletResponse response, ModelMap model)throws Exception{
        PlanResult ResponseResult = new PlanResult();
        response.setContentType("application/json;charset=utf-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        List<DishName> dishNames = new LinkedList<DishName>();
        List<MenuKey> menuKeys = sellerService.Menu(r_id);
        Iterator<MenuKey> iterator = menuKeys.iterator();
        while (iterator.hasNext()){
            MenuKey menuKey = iterator.next();
            DishName dishName = new DishName();
            dishName.setDishId(menuKey.getDishId());
            dishName.setDishName(dishMapper.selectByPrimaryKey(menuKey.getDishId()).getDishName());
            dishName.setDishPrice(dishMapper.selectByPrimaryKey(menuKey.getDishId()).getDishPrice());
            dishNames.add(dishName);
        }
        ResponseResult.setData(dishNames);
        ResponseResult.setMsg("获取菜单");
        ResponseResult.setStatus(true);
        String result = JsonUtils.ObjectToJson(ResponseResult);
        out.write(result);
        out.close();
    }

    //商家添加菜品
    @RequestMapping(value = "/Seller/{r_id}/add_dish", method = RequestMethod.GET)
    public void AddDish(@PathVariable("r_id")String r_id,@RequestParam("dish_name")String dish_name, @RequestParam("dish_price")int dish_price,
                          /*@RequestParam("dish_description")String dish_description, @RequestParam("dish_picture")String dish_picture,
                          @RequestParam("dish_type")String dish_type,*/
                          HttpServletRequest request, HttpServletResponse response, ModelMap model)throws Exception{
        PlanResult ResponseResult = new PlanResult();
//        Map<String, Object> resultMap = new HashMap<>();
        response.setContentType("application/json;charset=utf-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        if (sellerService.AddDish(dish_name, dish_price, /*dish_description, dish_picture, dish_type,*/ r_id)){
//            resultMap.put("account", seller.getAccount());
//            resultMap.put("password",false);
//            resultMap.put("user_id", seller.getSellerId());
//            resultMap.put("r_id", seller.getrId());
//            ResponseResult.setData(resultMap);
            ResponseResult.setMsg("添加成功");
            ResponseResult.setStatus(true);
            String result = JsonUtils.ObjectToJson(ResponseResult);
            out.write(result);
//            request.getRequestDispatcher("/Seller/index").forward(request, response);
            out.close();
        }else {
            ResponseResult.setMsg("添加失败");
            ResponseResult.setStatus(true);
            String result = JsonUtils.ObjectToJson(ResponseResult);
            out.write(result);
//            response.sendRedirect("/Seller/index");
            out.close();
        }
    }

    //商家删除菜品
    @RequestMapping(value = "/Seller/{r_id}/delete_dish", method = RequestMethod.GET)
    public void DeleteDish(@PathVariable("r_id")String r_id, @RequestParam("dish_id")String dish_id,
                           HttpServletRequest request, HttpServletResponse response, ModelMap model)throws Exception{
        PlanResult ResponseResult = new PlanResult();
        response.setContentType("application/json;charset=utf-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        if(sellerService.DeleteDish(r_id, dish_id)){
            ResponseResult.setMsg("删除成功");
            ResponseResult.setStatus(true);
            String result = JsonUtils.ObjectToJson(ResponseResult);
            out.write(result);
//            response.sendRedirect("/Seller/index");
            out.close();
        }else{
            ResponseResult.setMsg("删除失败");
            ResponseResult.setStatus(false);
            String result = JsonUtils.ObjectToJson(ResponseResult);
            out.write(result);
//            response.sendRedirect("/Seller/index");
            out.close();
        }
    }

    @Autowired
    private DishMapper dishMapper;

    //商家修改菜品
    @RequestMapping(value = "/Seller/{r_id}/modify_dish", method = RequestMethod.GET)
    public void ModifyDish(@PathVariable("r_id")String r_id, @RequestParam("dish_id")String dish_id,
                           @RequestParam("dish_name") String dish_name,@RequestParam("dish_price") int dish_price,
                           /*@RequestParam("dish_description") String dish_description,@RequestParam("dish_picture") String dish_picture,
                           @RequestParam("dish_type") String dish_type, */HttpServletRequest request, HttpServletResponse response, ModelMap model)throws Exception{
        Dish dish = new Dish();
        PlanResult ResponseResult = new PlanResult();
        Map<String, Object> resultMap = new HashMap<>();
        response.setContentType("application/json;charset=utf-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        if(sellerService.ModifyDish(r_id, dish_id, dish_name, dish_price/*, dish_description, dish_picture, dish_type*/)){
            ResponseResult.setMsg("修改成功");
            ResponseResult.setStatus(true);
            dish = dishMapper.selectByPrimaryKey(dish_id);
            resultMap.put("dish_name", dish.getDishName());
            resultMap.put("dish_price", dish.getDishPrice());
            /*resultMap.put("dish_description", dish.getdDescription());
            resultMap.put("dish_picture", dish.getDishPicture());
            resultMap.put("dish_type", dish.getDishType());*/
            ResponseResult.setData(resultMap);
            String result = JsonUtils.ObjectToJson(ResponseResult);
            out.write(result);
//            request.getRequestDispatcher("/Seller/index").forward(request, response);
            out.close();
        }else {
            ResponseResult.setMsg("修改失败");
            ResponseResult.setStatus(true);
            String result = JsonUtils.ObjectToJson(ResponseResult);
            out.write(result);
//            response.sendRedirect("/Seller/index");
            out.close();
        }
    }

    //商家获取订单列表
    public void CheckOrder(@PathVariable("r_id")String r_id, HttpServletRequest request, HttpServletResponse response, ModelMap model)throws Exception{
        List<OrderDetail> orders = new LinkedList<OrderDetail>();
        PlanResult ResponseResult = new PlanResult();
        response.setContentType("application/json;utf-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        orders = sellerService.OrderList(r_id);
        ResponseResult.setStatus(true);
        ResponseResult.setMsg("订单详情");
        ResponseResult.setData(orders);
        String result = JsonUtils.ObjectToJson(ResponseResult);
        out.write(result);
        out.close();
    }

    //商家接受订单
    @RequestMapping(value = "/Seller/order/{r_id}/{order_id}/accept", method = RequestMethod.GET)
    public void AcceptOrder(@PathVariable("r_id")String r_id, @PathVariable("order_id")String order_id,
                            HttpServletRequest request, HttpServletResponse response, ModelMap model)throws Exception{
        PlanResult ResponseResult = new PlanResult();
        response.setContentType("application/json;utf-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        Order order = sellerService.AcceptOrder(r_id, order_id);
        ResponseResult.setStatus(true);
        ResponseResult.setMsg("订单接受");
        ResponseResult.setData(order);
        String result = JsonUtils.ObjectToJson(ResponseResult);
        out.write(result);
        out.close();
    }

    //商家查看已达成合作关系
    @RequestMapping(value = "/Seller/{r_id}/coop/finish", method = RequestMethod.GET)
    public void FinishCoopList(@PathVariable("r_id") String r_id, HttpServletRequest request,
                               HttpServletResponse response, ModelMap model) throws Exception{
        PlanResult ResponseResult = new PlanResult();
        response.setContentType("application/json;charset=utf-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        List<DriverName> driverNames = sellerService.FinishCoopList(r_id);
        ResponseResult.setData(driverNames);
        ResponseResult.setStatus(true);
        ResponseResult.setMsg("已达成合作列表");
        String result = JsonUtils.ObjectToJson(ResponseResult);
        out.write(result);
        out.close();
    }
    //商家查看申请中的合作关系
    @RequestMapping(value = "/Seller/{r_id}/coop/apply", method = RequestMethod.GET)
    public void ApplyCoopList(@PathVariable("r_id") String r_id, HttpServletRequest request,
                               HttpServletResponse response, ModelMap model) throws Exception{
        PlanResult ResponseResult = new PlanResult();
        response.setContentType("application/json;charset=utf-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        List<DriverName> driverNames = sellerService.ApplyCoopList(r_id);
        ResponseResult.setData(driverNames);
        ResponseResult.setStatus(true);
        ResponseResult.setMsg("申请中合作列表");
        String result = JsonUtils.ObjectToJson(ResponseResult);
        out.write(result);
        out.close();
    }

    //商家解除或取消合作关系
    @RequestMapping(value = "/Seller/{r_id}/{driver_id}/coop/cancel", method = RequestMethod.GET)
    public void CancelCoop(@PathVariable("r_id") String r_id, @PathVariable("driver_id") String driver_id,
                           HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception{
        sellerService.CancelCoop(r_id, driver_id);
        PlanResult ResponseResult = new PlanResult();
        response.setContentType("application/json;charset=utf-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        ResponseResult.setStatus(true);
        ResponseResult.setMsg("取消成功");
        String result = JsonUtils.ObjectToJson(ResponseResult);
        out.write(result);
        out.close();
    }
}
