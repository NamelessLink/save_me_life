package com.lxs.controller;

import com.lxs.dao.OrderMapper;
import com.lxs.entity.Driver;
import com.lxs.entity.Order;
import com.lxs.otherentity.OrderDetail;
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

    //
}
