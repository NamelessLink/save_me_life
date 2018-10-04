package com.lxs.dao;

import com.lxs.entity.Menu;
import com.lxs.entity.MenuKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MenuMapper {
    int deleteByPrimaryKey(MenuKey key);

    int insert(Menu record);

    int insertSelective(Menu record);

    Menu selectByPrimaryKey(MenuKey key);

    int updateByPrimaryKeySelective(Menu record);

    int updateByPrimaryKey(Menu record);

    //自己写的
    List<MenuKey> selectByRid(@Param("r_id") String r_id);

    MenuKey selectByPrimaryKey(@Param("r_id") String r_id, @Param("dish_id") String dish_id);
}