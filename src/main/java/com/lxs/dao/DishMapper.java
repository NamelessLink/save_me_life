package com.lxs.dao;

import com.lxs.entity.Dish;

public interface DishMapper {
    int deleteByPrimaryKey(String dishId);

    int insert(Dish record);

    int insertSelective(Dish record);

    Dish selectByPrimaryKey(String dishId);

    int updateByPrimaryKeySelective(Dish record);

    int updateByPrimaryKey(Dish record);

    //自己写的
    Dish selectByDish_name(String dishName);
}