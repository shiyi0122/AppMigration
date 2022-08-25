package com.jxzy.AppMigration.NavigationApp.dao;

import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotBroadcastSaturation;

import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2022/8/22 16:37
 */
public interface SysScenicSpotBroadcastSaturationMapper {


    List<SysScenicSpotBroadcastSaturation> selectBySearch(Map<String, Object> search);

    int addBroadSaturation(SysScenicSpotBroadcastSaturation sysScenicSpotBroadcastSaturationNew);

    int editBroadSaturation(SysScenicSpotBroadcastSaturation sysScenicSpotBroadcastSaturation);


}
