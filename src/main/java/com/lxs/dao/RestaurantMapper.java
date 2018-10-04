package com.lxs.dao;

import com.lxs.entity.Restaurant;

import java.util.List;

public interface RestaurantMapper {
    int deleteByPrimaryKey(String rId);

    int insert(Restaurant record);

    int insertSelective(Restaurant record);

    Restaurant selectByPrimaryKey(String rId);

    int updateByPrimaryKeySelective(Restaurant record);

    int updateByPrimaryKey(Restaurant record);

    //自己写的
    List<Restaurant> selectAllRestaurants();
}