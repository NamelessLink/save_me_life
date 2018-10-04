package com.lxs.dao;

import com.lxs.entity.Driver;
import org.apache.ibatis.annotations.Param;

public interface DriverMapper {
    int deleteByPrimaryKey(String driverId);

    int insert(Driver record);

    int insertSelective(Driver record);

    Driver selectByPrimaryKey(String driverId);

    int updateByPrimaryKeySelective(Driver record);

    int updateByPrimaryKey(Driver record);

    //自己写的
    Driver findDriverByLogin(@Param("account") String account, @Param("pwd") String pwd);

    Driver findDriverByAccount(@Param("account") String account);
}