package com.jxzy.AppMigration.NavigationApp.dao;

import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotBroadcast;

import java.util.List;
import java.util.Map;

public interface SysScenicSpotBroadcastMapper {
    int deleteByPrimaryKey(Long broadcastId);

    int insert(SysScenicSpotBroadcast record);

    int insertSelective(SysScenicSpotBroadcast record);

    SysScenicSpotBroadcast selectByPrimaryKey(Long broadcastId);

    int updateByPrimaryKeySelective(SysScenicSpotBroadcast record);

    int updateByPrimaryKey(SysScenicSpotBroadcast record);

    /**
     * 
     * @param: search map对象
     * @description: TODO
     * @author: qushaobei
     * @date: 2021/11/4 0004
     */
    List<SysScenicSpotBroadcast> queryWordsScenicSpotBroadcast(Map<String, Object> search);

    /**
     * 查询景区停靠点
     * @param: search
     * @description: TODO
     * @author: qushaobei
     * @date: 2021/11/4 0004
     */
    List<SysScenicSpotBroadcast> queryScenicSpotStop(Map<String, Object> search);
}