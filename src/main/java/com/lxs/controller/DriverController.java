package com.lxs.controller;

import com.lxs.dao.OrderMapper;
import com.lxs.entity.Cooperation;
import com.lxs.entity.Driver;
import com.lxs.entity.Order;
import com.lxs.otherentity.OrderDetail;
import com.lxs.otherentity.RestaurantName;
import com.lxs.service.DriverService;
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
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Controller
public class DriverController {

    @Autowired
    private DriverService driverService;

    @RequestMapping(value = "/Driver/index")
    public String user_index(){return "/Driver/driver_index";}

    //骑手登录
    @RequestMapping(value = "/Driver/login",  method = RequestMethod.GET)
    public void DriverLogin(@RequestParam("account")String account, @RequestParam("pwd")String pwd,
                            HttpServletRequest request, HttpServletResponse response, ModelMap model)throws Exception{
        PlanResult ResponseResult = new PlanResult();
        Map<String, Object> resultMap = new HashMap<>();
        response.setContentType("application/json;charset=utf-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        Driver driver = null;
        //账号存在且和密码匹配,进入首页
        int vaild_tag = driverService.isVaildDriver(account, pwd);
        if(vaild_tag == 1){
            driver = driverService.driverLogin(account, pwd);
            resultMap.put("account", driver.getAccount());
            resultMap.put("password",false);
            resultMap.put("driver_id", driver.getDriverId());
            resultMap.put("driver_name", driver.getDriverName());
            ResponseResult.setData(resultMap);
            ResponseResult.setMsg("登录成功");
            ResponseResult.setStatus(true);
            String result = JsonUtils.ObjectToJson(ResponseResult);
            out.write(result);
            out.close();
        }
        //账号不存在，返回首页
        else {
            if (vaild_tag == 0){
                ResponseResult.setMsg("账号或密码错误");
                ResponseResult.setStatus(false);
                String result = JsonUtils.ObjectToJson(ResponseResult);
                out.write(result);
                out.close();
            }else if (vaild_tag == -1){
                ResponseResult.setMsg("账号或密码为空");
                ResponseResult.setStatus(false);
                String result = JsonUtils.ObjectToJson(ResponseResult);
                out.write(result);
                out.close();
            }
        }
    }

    //骑手查看订单
    @RequestMapping(value = "/Driver/order/{driver_id}", method = RequestMethod.GET)
    public void CheckOrder(@PathVariable("driver_id") String driver_id, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception{
        List<OrderDetail> orders = new LinkedList<OrderDetail>();
        PlanResult ResponseResult = new PlanResult();
        response.setContentType("application/json;utf-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        orders = driverService.OrderList(driver_id);
        ResponseResult.setStatus(true);
        ResponseResult.setMsg("订单详情");
        ResponseResult.setData(orders);
        String result = JsonUtils.ObjectToJson(ResponseResult);
        out.write(result);
        out.close();
    }

    //骑手派送订单
    @RequestMapping(value = "/Driver/order/{driver_id}/{order_id}/deliver", method = RequestMethod.GET)
    public void DeliverOrder(@PathVariable("driver_id") String driver_id, @PathVariable("order_id") String order_id, HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception{
        PlanResult ResponseResult = new PlanResult();
        response.setContentType("application/json;utf-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        Order order = driverService.AcceptOrder(driver_id, order_id);
        ResponseResult.setStatus(true);
        ResponseResult.setMsg("订单接受");
        ResponseResult.setData(order);
        String result = JsonUtils.ObjectToJson(ResponseResult);
        out.write(result);
        out.close();
    }

    //骑手获得餐馆列表
    @RequestMapping(value = "/Driver/restaurant", method = RequestMethod.GET)
    public void RestaurantList(HttpServletResponse response, HttpServletRequest request, ModelMap model) throws Exception{
        PlanResult ResponseResult = new PlanResult();
        List<RestaurantName> restaurantName = new LinkedList<RestaurantName>();
        restaurantName = driverService.RestaurantList();
        response.setContentType("application/json;charset=utf-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        ResponseResult.setData(restaurantName);
        ResponseResult.setStatus(true);
        ResponseResult.setMsg("获取所有餐馆");
        String result = JsonUtils.ObjectToJson(ResponseResult);
        out.write(result);
        out.close();
    }

    //骑手向餐馆发起合作申请
    @RequestMapping(value = "/Driver/{driver_id}/{r_id}/apply", method = RequestMethod.GET)
    public void CoopApply(@PathVariable("driver_id") String driver_id, @PathVariable("r_id") String r_id,
                          HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception{
        PlanResult ResponseResult = new PlanResult();
        response.setContentType("application/json;charset=utf-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        Cooperation cooperation = driverService.CoopApply(driver_id, r_id);
        if(cooperation != null){
            ResponseResult.setData(cooperation);
            ResponseResult.setStatus(true);
            ResponseResult.setMsg("申请成功");
        }else{
            ResponseResult.setStatus(false);
            ResponseResult.setMsg("已经申请过或者已经处于合作状态");
        }
        String result = JsonUtils.ObjectToJson(ResponseResult);
        out.write(result);
        out.close();
    }

    //骑手查看已达成合作关系
    @RequestMapping(value = "/Driver/{driver_id}/coop/finish", method = RequestMethod.GET)
    public void FinishCoopList(@PathVariable("driver_id") String driver_id, HttpServletRequest request,
                               HttpServletResponse response, ModelMap model) throws Exception{
        PlanResult ResponseResult = new PlanResult();
        response.setContentType("application/json;charset=utf-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        List<RestaurantName> restaurantNames = restaurantNames = driverService.FinishCoopList(driver_id);
        ResponseResult.setData(restaurantNames);
        ResponseResult.setStatus(true);
        ResponseResult.setMsg("已达成合作列表");
        String result = JsonUtils.ObjectToJson(ResponseResult);
        out.write(result);
        out.close();
    }

    //骑手查看在申请中的合作关系
    @RequestMapping(value = "/Driver/{driver_id}/coop/apply", method = RequestMethod.GET)
    public void ApplyCoopList(@PathVariable("driver_id") String driver_id, HttpServletRequest request,
                               HttpServletResponse response, ModelMap model) throws Exception{
        PlanResult ResponseResult = new PlanResult();
        response.setContentType("application/json;charset=utf-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        List<RestaurantName> restaurantNames = restaurantNames = driverService.ApplyCoopList(driver_id);
        ResponseResult.setData(restaurantNames);
        ResponseResult.setStatus(true);
        ResponseResult.setMsg("申请中合作列表");
        String result = JsonUtils.ObjectToJson(ResponseResult);
        out.write(result);
        out.close();
    }

    //骑手解除或取消合作关系
    @RequestMapping(value = "/Driver/{driver_id}/{r_id}/coop/cancel", method = RequestMethod.GET)
    public void CancelCoop(@PathVariable("driver_id") String driver_id, @PathVariable("r_id") String r_id,
                           HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception{
        PlanResult ResponseResult = new PlanResult();
        response.setContentType("application/json;charset=utf-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        driverService.CancelCoop(driver_id, r_id);
        ResponseResult.setStatus(true);
        ResponseResult.setMsg("取消成功");
        String result = JsonUtils.ObjectToJson(ResponseResult);
        out.write(result);
        out.close();
    }
}
