package com.lxs.service;

import com.lxs.dao.*;
import com.lxs.entity.*;
import com.lxs.otherentity.OrderDetail;
import com.lxs.util.SnowFlakeIdWorker;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import static com.lxs.util.MD5.md5;

@Service
public class SellerService {

    @Autowired
    private SellerMapper sellerMapper;
    @Autowired
    private MenuMapper menuMapper;
    @Autowired
    private DishMapper dishMapper;

    //验证账号是否存在
    public boolean isSellerExist(String account){
        if(sellerMapper.findSellerByAccount(account) == null){
            return false;
        }else{
            return true;
        }
    }

    //验证是否合法,1合法，0账号或密码错误，-1账号或密码为空，
    public int isVaildSeller(String account, String pwd){
        Seller seller = new Seller();
        if (account != null && !"".equals(account)){
            seller = sellerMapper.findSellerByAccount(account);
            if (seller == null){
                return 0;
            }else{
                if (pwd != null && !"".equals(pwd)){
                    if (seller.getPwd().equals(md5(pwd))){
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

    //卖家登录
    public Seller sellerLogin(String account, String pwd)throws Exception{
        return sellerMapper.findSellerByLogin(account, md5(pwd));
    }

    //卖家菜单
    public List<MenuKey> Menu(@Param("r_id")String r_id){
        List<MenuKey> menuKeys = menuMapper.selectByRid(r_id);
        return menuKeys;
    }

    //添加菜品
    public boolean AddDish(String dish_name, int dish_price, /*String dish_description, String dish_picture, String dish_type,*/ String r_id) throws Exception{
        Dish dish = dishMapper.selectByDish_name(dish_name);
        if(dish != null){
            if(menuMapper.selectByPrimaryKey(r_id, dish.getDishId()) != null ){
                return false;
            }else{
                Menu menu = new Menu();
                menu.setrId(r_id);
                menu.setDishId(dish.getDishId());
                menuMapper.insert(menu);
                return true;
            }
        }else{
            Dish new_dish = new Dish();
            SnowFlakeIdWorker snowFlakeIdWorker = new SnowFlakeIdWorker(0, 15);
            new_dish.setDishId(String.valueOf(snowFlakeIdWorker.nextId()));
            new_dish.setDishName(dish_name);
            new_dish.setDishPrice(dish_price);
            /*new_dish.setdDescription(dish_description);
            new_dish.setDishPicture(dish_picture);
            new_dish.setDishType(dish_type);*/
            dishMapper.insert(new_dish);
            Menu menu = new Menu();
            menu.setrId(r_id);
            menu.setDishId(new_dish.getDishId());
            menuMapper.insert(menu);
            return true;
        }
    }

    //卖家删除菜品
    public boolean DeleteDish(@Param("r_id")String r_id, @Param("dish_id")String dish_id){
        MenuKey menu = menuMapper.selectByPrimaryKey(r_id, dish_id);
        if (menu != null){
            menuMapper.deleteByPrimaryKey(menu);
            return true;
        }else{
            return false;
        }
    }

    //卖家修改菜品
    public boolean ModifyDish(@Param("r_id")String r_id, @Param("dish_id")String dish_id,
                              @Param("dish_name") String dish_name,@Param("dish_price") int dish_price/*,
                              @Param("dish_description") String dish_description,@Param("dish_picture") String dish_picture,
                              @Param("dish_type") String dish_type*/) {
        int tag = 0;
        Dish dish = dishMapper.selectByPrimaryKey(dish_id);
        if (dish_name != null && !"".equals(dish_name) && !dish.getDishName().equals(dish_name)) {
            dish.setDishName(dish_name);
            tag++;
        }
        if (dish_price > 0 && dish.getDishPrice() != dish_price) {
            dish.setDishPrice(dish_price);
            tag++;
        }
        /*
        if (dish_description != null && !"".equals(dish_description) && !dish.getdDescription().equals(dish_description)) {
            dish.setdDescription(dish_description);
            tag++;
        }
        if (dish_picture != null && !"".equals(dish_picture) && !dish.getDishPicture().equals(dish_picture)) {
            dish.setDishPicture(dish_picture);
            tag++;
        }
        if (dish_type != null && !"".equals(dish_type) && !dish.getDishType().equals(dish_type)) {
            dish.setDishType(dish_type);
            tag++;
        }*/
        dishMapper.updateByPrimaryKey(dish);
        if (tag != 0){
            return true;
        }else {
            return false;
        }
    }

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private RestaurantMapper restaurantMapper;

//    //用户查看订单状态
//    public List<OrderDetail> CheckOrder(@Param("r_id")String r_id){
//        List<Order> orders = new LinkedList<Order>();
//        orders = orderMapper.selectByRid(r_id);
//        List<OrderDetail> orderDetails = new LinkedList<OrderDetail>();
//        Iterator<Order> iterator = orders.iterator();
//        while (iterator.hasNext()){
//            Order order = iterator.next();
//            OrderDetail orderDetail = new OrderDetail();
//            orderDetail.setOrderId(order.getOrderId());
//            orderDetail.setState(order.getState());
//            orderDetail.setCreateDate(order.getCreateDate());
//            orderDetail.setSendAddr(order.getSendAddr());
//            orderDetail.setrName(restaurantMapper.selectByPrimaryKey(order.getrId()).getrName());
//            orderDetail.setDishName(dishMapper.selectByPrimaryKey(order.getDishId()).getDishName());
//            orderDetail.setDishPrice(dishMapper.selectByPrimaryKey(order.getDishId()).getDishPrice());
//            orderDetails.add(orderDetail);
//        }
//        return orderDetails;
//    }

    //商家获取未接受订单
    public List<Order> OrderList(@Param("r_id")String r_id){
        List<Order> orders = new LinkedList<Order>();
        orders = orderMapper.selectByRid(r_id);
        return orders;
    }

    @Autowired
    private DriverMapper driverMapper;
    @Autowired
    private CooperationMapper cooperationMapper;

    //商家指定骑手并接单
    public Order AcceptOrder(@Param("r_id")String r_id ,@Param("order_id")String order_id){
        Order order = orderMapper.selectByPrimaryKey(order_id);
        List<CooperationKey> cooperationKeys = new LinkedList<CooperationKey>();
        cooperationKeys = cooperationMapper.selectByRid(r_id);
        order.setDriverId(cooperationKeys.get(0).getDriverId());
        order.setExpectDate(new Date());
        order.setState(1);
        orderMapper.updateByPrimaryKeySelective(order);
        return order;
    }
}
