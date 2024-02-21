package com.jxzy.AppMigration.NavigationApp.dao;

import com.jxzy.AppMigration.NavigationApp.entity.Excel.SysScenicSpotBroadcastExcel;
import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotBroadcast;
import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotBroadcastExtendWithBLOBs;
import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotBroadcastWithBlogs;

import java.util.List;
import java.util.Map;

public interface SysScenicSpotBroadcastMapper {
    int deleteByPrimaryKey(Long broadcastId);

    int insert(SysScenicSpotBroadcast record);


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

    Integer selectSpotByCount(Long scenicSpotId);

    /**
     * 查询景点排行
     * @param: search
     * @description: TODO
     * @return: java.util.List<com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotBroadcast>
     * @author: qushaobei
     * @date: 2022/8/4 0004
     */
    List<SysScenicSpotBroadcast> queryWordsScenicSpotBroadcastList(Map<String, Object> search);

    List<SysScenicSpotBroadcast> getSpotBroadcastList(Map<String, Object> search);


    int addHotSpotBroadcast(Long id);

    List<SysScenicSpotBroadcast> getSpotBroadcastGps(String s);

    List<SysScenicSpotBroadcastWithBlogs> getSpotBroadcastListNew(Map<String, Object> search);

    List<SysScenicSpotBroadcastExtendWithBLOBs> getBroadcastDetails(Map<String, Object> search);


    int insertSelective(SysScenicSpotBroadcast broadcast);

    List<SysScenicSpotBroadcastExcel> uploadExcelSpotBroadcast(Map<String, Object> search);


    SysScenicSpotBroadcast getSpotBroadcastIdMinimum(Map<String, Object> search);

    SysScenicSpotBroadcast getSpotBroadcastName(String broadcastName);

    SysScenicSpotBroadcast getSpotIdAndBroadcastName(Long scenicSpotId, String broadcastName);

    List<SysScenicSpotBroadcast> getSpotIdAndBroadcastNameList(Long scenicSpotFid, String content);


    List<SysScenicSpotBroadcast> getSpotBroadcastNameList(String content);


    List<SysScenicSpotBroadcastWithBlogs> getSpotBroadcastListNewT(Map<String, Object> search);

    List<SysScenicSpotBroadcast> getSpotIdByBroadcastDropDown(String spotId);

}