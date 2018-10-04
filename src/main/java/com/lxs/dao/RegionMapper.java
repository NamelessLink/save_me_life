package com.lxs.dao;

import com.lxs.entity.Region;
import com.lxs.entity.RegionKey;

public interface RegionMapper {
    int deleteByPrimaryKey(RegionKey key);

    int insert(Region record);

    int insertSelective(Region record);

    Region selectByPrimaryKey(RegionKey key);

    int updateByPrimaryKeySelective(Region record);

    int updateByPrimaryKey(Region record);


}