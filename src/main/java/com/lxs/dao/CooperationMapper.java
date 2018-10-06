package com.lxs.dao;

import com.lxs.entity.Cooperation;
import com.lxs.entity.CooperationKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CooperationMapper {
    int deleteByPrimaryKey(CooperationKey key);

    int insert(Cooperation record);

    int insertSelective(Cooperation record);

    Cooperation selectByPrimaryKey(CooperationKey key);

    int updateByPrimaryKeySelective(Cooperation record);

    int updateByPrimaryKey(Cooperation record);

    //自己写的
    List<Cooperation> selectByRid(@Param("r_id") String r_id);

    List<Cooperation> selectByDid(@Param("driver_id") String driver_id);
}