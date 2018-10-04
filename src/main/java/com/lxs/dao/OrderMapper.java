package com.lxs.dao;

import com.lxs.entity.Order;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderMapper {
    int deleteByPrimaryKey(String orderId);

    int insert(Order record);

    int insertSelective(Order record);

    Order selectByPrimaryKey(String orderId);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);

    //自己写的
    List<Order> selectByRid(@Param("r_id")String r_id);

    List<Order> selectByUid(@Param("u_id")String u_id);
}