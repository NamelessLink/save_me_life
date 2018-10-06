package com.lxs.service;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lxs.dao.*;
import com.lxs.entity.*;
import com.lxs.otherentity.DishName;
import com.lxs.otherentity.OrderDetail;
import com.lxs.otherentity.RestaruantDetail;
import com.lxs.otherentity.RestaurantName;
import com.lxs.util.SnowFlakeIdWorker;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import static com.lxs.util.DateUtil.ExpectDate;
import static com.lxs.util.MD5.md5;


@Service
public class UserService {
    @Autowired
    private CustomerMapper userMapper;

    //验证账号是否存在
    public boolean isUserExist(String account){
        if(userMapper.findUserByAccount(account) == null){
            return false;
        }else{
            return true;
        }
    }

    //验证是否合法,1合法，0账号或密码错误，-1账号或密码为空，
    public int isVaildCustomer(String account, String pwd){
        Customer user = new Customer();
        if (account != null && !"".equals(account)){
            user = userMapper.findUserByAccount(account);
            if (user == null){
                return 0;
            }else{
                if (pwd != null && !"".equals(pwd)){
                    if (user.getPwd().equals(md5(pwd))){
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

    //向库中插入账号
    public void AddUser(String account, String pwd, String phone) {
        Customer adduser = new Customer();
        SnowFlakeIdWorker snowflakeid = new SnowFlakeIdWorker(1, 15);
        adduser.setUserId(String.valueOf(snowflakeid.nextId()));
        adduser.setAccount(account);
        adduser.setPwd(md5(pwd));
        adduser.setPhone(phone);
        userMapper.insertSelective(adduser);
    }

    //账号登录
    public Customer UserLogin(String account, String pwd) throws Exception {
        return userMapper.findUserByLogin(account, md5(pwd));
    }

    //账号信息修改
    public boolean UserModify(String u_id, String pwd, String name,String phone, String addr){
        int tag = 0;
        Customer user = userMapper.selectByPrimaryKey(u_id);
        if(user.getAddr() == null){
            user.setAddr(" ");
        }
        if(user.getUserName() == null){
            user.setUserName(" ");
        }
        if(!pwd.equals("undefined") && pwd != null && !"".equals(pwd) && !user.getPwd().equals(md5(pwd))){
            user.setPwd(md5(pwd));
            tag++;
        }
        if(!name.equals("undefined") && name != null && !"".equals(pwd) && !user.getUserName().equals(name)){
            user.setUserName(name);
            tag++;
        }
        if(!addr.equals("undefined") && addr != null && !"".equals(pwd) && !user.getAddr().equals(addr)){
            user.setAddr(addr);
            tag++;
        }
        if(!phone.equals("undefined") && phone != null && !"".equals(phone) && !user.getPhone().equals(phone)){
            user.setPhone(phone);
            tag++;
        }
        userMapper.updateByPrimaryKey(user);
        if(tag != 0){
            return true;
        }else{
            return false;
        }
    }

    @Autowired
    private RestaurantMapper restaurantMapper;

    //获取餐馆
    public List<RestaurantName> RequestRestaruants() throws Exception{
        List<RestaurantName> restaurantNames = new LinkedList<RestaurantName>();
        List<Restaurant> restaurants = new LinkedList<Restaurant>();
        restaurants = restaurantMapper.selectAllRestaurants();
        Iterator<Restaurant> iterator = restaurants.iterator();
        while(iterator.hasNext()){
            RestaurantName restaurantName = new RestaurantName();
            Restaurant restaurant = new Restaurant();
            restaurant = iterator.next();
            restaurantName.setrId(restaurant.getrId());
            restaurantName.setrName(restaurant.getrName());
//            restaurantName.setDishes(RequestDetail(restaurant.getrId()));
            restaurantNames.add(restaurantName);
        }
        return restaurantNames;
    }

    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private MenuMapper menuMapper;

    //用户查询个人信息
    public Customer UserInfo(@Param("u_id")String u_id)throws Exception{
        Customer user = userMapper.selectByPrimaryKey(u_id);
        return user;
    }

    //获取餐馆菜单和详情
    public List<DishName> RequestDetail(@Param("r_id")String r_id) throws Exception{
        List<MenuKey> selectDishes = menuMapper.selectByRid(r_id);
        List<DishName> dishes = new LinkedList<DishName>();
        Restaurant restaurant = restaurantMapper.selectByPrimaryKey(r_id);
        Iterator<MenuKey> iterator = selectDishes.iterator();
        RestaruantDetail restaruantDetail = new RestaruantDetail();
        while(iterator.hasNext()){
            Dish dish = dishMapper.selectByPrimaryKey(iterator.next().getDishId());
            DishName dishName = new DishName();
            dishName.setDishId(dish.getDishId());
            dishName.setDishName(dish.getDishName());
            dishName.setDishPrice(dish.getDishPrice());
            dishes.add(dishName);
        }
//        restaruantDetail.setR_id(r_id);
//        restaruantDetail.setDishes(dishes);
//        restaruantDetail.setR_name(restaurant.getrName());
        return dishes;
    }

    //获取菜品详情
    public Dish DishDetail(@Param("dish_id")String dish_id){
        Dish dish = dishMapper.selectByPrimaryKey(dish_id);
        return dish;
    }

    @Autowired
    private OrderMapper orderMapper;

    //用户生成订单
    public void SubmitOrder(@Param("u_id")String u_id, @Param("r_id")String r_id,
                               @Param("dish_id")String dish_id){
        SnowFlakeIdWorker snowFlakeIdWorker = new SnowFlakeIdWorker(1,15);
        Order order = new Order();
        order.setOrderId(String.valueOf(snowFlakeIdWorker.nextId()));
        order.setCreateDate(new Date());
        order.setState(0);
        order.setSendAddr(userMapper.selectByPrimaryKey(u_id).getAddr());
        order.setrId(r_id);
        order.setDishId(dish_id);
        order.setUserId(u_id);
        Menu menu = new Menu();
        menu = menuMapper.selectByPrimaryKey(r_id, dish_id);
        menu.setSales(menu.getSales() + 1);
        menuMapper.updateByPrimaryKeySelective(menu);
        orderMapper.insertSelective(order);
    }

    //用户查看订单状态
    public List<OrderDetail> CheckOrder(@Param("u_id")String u_id){
        List<Order> orders = new LinkedList<Order>();
        orders = orderMapper.selectByUid(u_id);
        List<OrderDetail> orderDetails = new LinkedList<OrderDetail>();
        Iterator<Order> iterator = orders.iterator();
        while (iterator.hasNext()){
            Order order = iterator.next();
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrderId(order.getOrderId());
            if (order.getState() == 0){
                orderDetail.setState("未接单");
            }else if(order.getState() == 1){
                orderDetail.setState("已接单");
            }else{
                orderDetail.setState("派送中");
            }
            orderDetail.setCreateDate(order.getCreateDate());
            orderDetail.setSendAddr(order.getSendAddr());
            orderDetail.setrName(restaurantMapper.selectByPrimaryKey(order.getrId()).getrName());
            orderDetail.setDishName(dishMapper.selectByPrimaryKey(order.getDishId()).getDishName());
            orderDetail.setDishPrice(dishMapper.selectByPrimaryKey(order.getDishId()).getDishPrice());
            orderDetails.add(orderDetail);
        }
        return orderDetails;
    }

    //用户完成订单
    public void FinishOrder(@Param("order_id")String order_id){
        Order order = orderMapper.selectByPrimaryKey(order_id);
        order.setState(-1);
        order.setEndDate(new Date());
        orderMapper.updateByPrimaryKeySelective(order);
    }
}