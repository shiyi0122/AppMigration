package com.jxzy.AppMigration.NavigationApp.Service;


import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotBroadcastSaturation;

import java.util.List;

/**
 * @Author zhang
 * @Date 2022/8/22 16:33
 * 景点饱和度相关接口
 */

public interface SysScenicSpotBroadcastSaturationService {

    int addSaturation(SysScenicSpotBroadcastSaturation sysScenicSPotBroadcastSaturation);

    List<SysScenicSpotBroadcastSaturation> getBroadcastSaturationList(String spotId);

    int reduceSaturation(Long broadcastId);

}
