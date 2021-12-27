package com.jxzy.AppMigration.NavigationApp.dao;

import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotBroadcast;
import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotParking;
import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotParkingWithBLOBs;

import java.util.List;
import java.util.Map;

public interface SysScenicSpotParkingMapper {
    int deleteByPrimaryKey(Long parkingId);

    int insert(SysScenicSpotParkingWithBLOBs record);

    int insertSelective(SysScenicSpotParkingWithBLOBs record);

    SysScenicSpotParkingWithBLOBs selectByPrimaryKey(Long parkingId);

    int updateByPrimaryKeySelective(SysScenicSpotParkingWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(SysScenicSpotParkingWithBLOBs record);

    int updateByPrimaryKey(SysScenicSpotParking record);

    /**
     * 获取景区停靠点列表
     * @param: search
     * @description: TODO
     * @author: qushaobei
     * @date: 2021/12/27 0027
     */
    List<SysScenicSpotBroadcast> getScenicSpotParkingList(Map<String, Object> search);
}