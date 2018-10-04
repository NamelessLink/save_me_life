package com.lxs.dao;

import com.lxs.entity.Customer;
import org.apache.ibatis.annotations.Param;

public interface CustomerMapper {
    int deleteByPrimaryKey(String userId);

    int insert(Customer record);

    int insertSelective(Customer record);

    Customer selectByPrimaryKey(String userId);

    int updateByPrimaryKeySelective(Customer record);

    int updateByPrimaryKey(Customer record);

    //自己写的
    Customer findUserByLogin(@Param("account") String account, @Param("pwd") String pwd);

    Customer findUserByAccount(@Param("account") String account);
}