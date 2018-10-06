package com.lxs.service;

import com.lxs.dao.*;
import com.lxs.entity.Cooperation;
import com.lxs.entity.Driver;
import com.lxs.entity.Order;
import com.lxs.entity.Restaurant;
import com.lxs.otherentity.OrderDetail;
import com.lxs.otherentity.RestaurantName;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.lxs.util.MD5.md5;
import static com.lxs.util.DateUtil.ExpectDate;

@Service
public class DriverService {

    @Autowired
    private DriverMapper driverMapper;

    //验证账号是否存在
    public boolean isDriverExist(String account){
        if(driverMapper.findDriverByAccount(account) == null){
            return false;
        }else{
            return true;
        }
    }

    //验证是否合法,1合法，0账号或密码错误，-1账号或密码为空，
    public int isVaildDriver(String account, String pwd){
        Driver driver = new Driver();
        if (account != null && !"".equals(account)){
            driver = driverMapper.findDriverByAccount(account);
            if (driver == null){
                return 0;
            }else{
                if (pwd != null && !"".equals(pwd)){
                    if (driver.getPwd().equals(md5(pwd))){
                        return 1;
                    }else{
                        return 0;
                    }
                }else{
                    return -1;
                }
            }
        }else{
            return -1;
        }
    }

    //骑手登录登录
    public Driver driverLogin(String account, String pwd) throws Exception{
        return driverMapper.findDriverByLogin(account, md5(pwd));
    }

    @Autowired
    private CooperationMapper cooperationMapper;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private RestaurantMapper restaurantMapper;

    //骑手查看订单
    public List<OrderDetail> OrderList(@Param("driver_id") String drive_id) throws Exception{
        List<Cooperation> cooperations = new LinkedList<Cooperation>();
        cooperations = cooperationMapper.selectByDid(drive_id);
        Iterator<Cooperation> iterator = cooperations.iterator();
        List<OrderDetail> orderDetails = new LinkedList<OrderDetail>();
        while(iterator.hasNext()){
            Cooperation cooperation = iterator.next();
            if(cooperation.getStatus() == 1){
                List<Order> orders = new LinkedList<Order>();
                orders = orderMapper.selectByRidD(cooperation.getrId());
                Iterator<Order> It_O = orders.iterator();
                while(It_O.hasNext()){
                    Order order = It_O.next();
                    OrderDetail orderDetail = new OrderDetail();
                    orderDetail.setOrderId(order.getOrderId());
                    orderDetail.setCreateDate(order.getCreateDate());
                    orderDetail.setSendAddr(order.getSendAddr());
                    orderDetail.setPhone(customerMapper.selectByPrimaryKey(order.getUserId()).getPhone());
                    orderDetail.setrName(restaurantMapper.selectByPrimaryKey(order.getrId()).getrName());
                    orderDetail.setUserName(customerMapper.selectByPrimaryKey(order.getUserId()).getUserName());
                    orderDetail.setDishName(dishMapper.selectByPrimaryKey(order.getDishId()).getDishName());
                    orderDetail.setDishPrice(dishMapper.selectByPrimaryKey(order.getDishId()).getDishPrice());
                    orderDetails.add(orderDetail);
                }
            }
        }
        return orderDetails;
    }

    //骑手派送订单
    public Order AcceptOrder(@Param("driver_id")String driver_id ,@Param("order_id")String order_id){
        Order order = orderMapper.selectByPrimaryKey(order_id);
        order.setExpectDate(ExpectDate(new Date()));
        order.setState(2);
        order.setDriverId(driver_id);
        orderMapper.updateByPrimaryKeySelective(order);
        return order;
    }

    //骑手获取餐馆列表
    public List<RestaurantName> RestaurantList(){
        List<RestaurantName> restaurantNames = new LinkedList<RestaurantName>();
        List<Restaurant> restaurants = restaurantMapper.selectAllRestaurants();
        Iterator<Restaurant> iterator = restaurants.iterator();
        while (iterator.hasNext()){
            Restaurant restaurant = iterator.next();
            RestaurantName restaurantName = new RestaurantName();
            restaurantName.setrName(restaurant.getrName());
            restaurantName.setrId(restaurant.getrId());
            restaurantNames.add(restaurantName);
        }
        return restaurantNames;
    }

    //骑手向餐馆发起合作申请
    public Cooperation CoopApply(String driver_id, String r_id){
        Cooperation cooperation = new Cooperation();
        cooperation.setDriverId(driver_id);
        cooperation.setrId(r_id);
        cooperationMapper.insertSelective(cooperation);
        return cooperationMapper.selectByPrimaryKey(cooperation);
    }

    //骑手查看已达成合作关系
    public List<RestaurantName> FinishCoopList(String driver_id){
        List<RestaurantName> restaurantNames = new LinkedList<RestaurantName>();
        List<Cooperation> cooperations = cooperationMapper.selectByDid(driver_id);
        Iterator<Cooperation> iterator = cooperations.iterator();
        while (iterator.hasNext()){
            Cooperation cooperation = iterator.next();
            if (cooperation.getStatus() == 1){
                Restaurant restaurant = restaurantMapper.selectByPrimaryKey(cooperation.getrId());
                RestaurantName restaurantName = new RestaurantName();
                restaurantName.setrName(restaurant.getrName());
                restaurantName.setrId(restaurant.getrId());
                restaurantNames.add(restaurantName);
            }
        }
        return restaurantNames;
    }

    //骑手查看申请中的合作关系
    public List<RestaurantName> ApplyCoopList(String driver_id){
        List<RestaurantName> restaurantNames = new LinkedList<RestaurantName>();
        List<Cooperation> cooperations = cooperationMapper.selectByDid(driver_id);
        Iterator<Cooperation> iterator = cooperations.iterator();
        while (iterator.hasNext()){
            Cooperation cooperation = iterator.next();
            if (cooperation.getStatus() == 0){
                Restaurant restaurant = restaurantMapper.selectByPrimaryKey(cooperation.getrId());
                RestaurantName restaurantName = new RestaurantName();
                restaurantName.setrId(restaurant.getrId());
                restaurantName.setrName(restaurant.getrName());
                restaurantNames.add(restaurantName);
            }
        }
        return restaurantNames;
    }

    //骑手解除或取消合作关系
    public void CancelCoop(String driver_id, String r_id){
        Cooperation cooperation = new Cooperation();
        cooperation.setrId(r_id);
        cooperation.setDriverId(driver_id);
        cooperationMapper.deleteByPrimaryKey(cooperation);
    }
}
