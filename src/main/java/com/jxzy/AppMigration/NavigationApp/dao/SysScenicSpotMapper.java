package com.jxzy.AppMigration.NavigationApp.dao;

import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpot;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    /**
     * 查询景区排行
     * @param: search
     * @description: TODO
     * @return: java.util.List<com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpot>
     * @author: qushaobei
     * @date: 2022/8/4 0004
     */
    List<SysScenicSpot> queryScenicSpotRankingList(Map<String, Object> search);

    List<SysScenicSpot> getScenicSpotAll(HashMap<String, Object> search);

}