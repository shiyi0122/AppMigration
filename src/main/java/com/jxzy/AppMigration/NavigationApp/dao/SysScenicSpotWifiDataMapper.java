package com.jxzy.AppMigration.NavigationApp.dao;

import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotWifiData;

public interface SysScenicSpotWifiDataMapper {
    int deleteByPrimaryKey(Long wifiId);

    int insert(SysScenicSpotWifiData record);

    int insertSelective(SysScenicSpotWifiData record);

    SysScenicSpotWifiData selectByPrimaryKey(Long wifiId);

    int updateByPrimaryKeySelective(SysScenicSpotWifiData record);

    int updateByPrimaryKey(SysScenicSpotWifiData record);
}