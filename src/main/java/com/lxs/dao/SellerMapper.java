package com.lxs.dao;

import com.lxs.entity.Seller;
import org.apache.ibatis.annotations.Param;

public interface SellerMapper {
    int deleteByPrimaryKey(String sellerId);

    int insert(Seller record);

    int insertSelective(Seller record);

    Seller selectByPrimaryKey(String sellerId);

    int updateByPrimaryKeySelective(Seller record);

    int updateByPrimaryKey(Seller record);

    //自己写的
    Seller findSellerByLogin(@Param("account") String account, @Param("pwd") String pwd);

    Seller findSellerByAccount(@Param("account") String account);
}