package com.jxzy.AppMigration.NavigationApp.dao;

import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpot;

import java.util.HashMap;
import java.util.List;

public interface SysScenicSpotMapper {
    int deleteByPrimaryKey(Long scenicSpotId);

    int insert(SysScenicSpot record);

    int insertSelective(SysScenicSpot record);

    SysScenicSpot selectByPrimaryKey(Long scenicSpotId);

    int updateByPrimaryKeySelective(SysScenicSpot record);

    int updateByPrimaryKey(SysScenicSpot record);

    List<SysScenicSpot> queryScenicSpotList();

    List<SysScenicSpot> selectBySearch(HashMap<String, Object> search);


    SysScenicSpot spotDetails(String spotId);

}