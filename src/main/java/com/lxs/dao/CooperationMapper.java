package com.lxs.dao;

import com.lxs.entity.CooperationKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CooperationMapper {
    int deleteByPrimaryKey(CooperationKey key);

    int insert(CooperationKey record);

    int insertSelective(CooperationKey record);

    //自己写的
    List<CooperationKey> selectByRid(@Param("r_id")String r_id);
}