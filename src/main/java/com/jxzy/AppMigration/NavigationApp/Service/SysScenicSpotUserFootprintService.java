package com.jxzy.AppMigration.NavigationApp.Service;

import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotUserFootprint;

import java.util.List;

/**
 * @Author zhang
 * @Date 2022/8/16 16:35
 */
public interface SysScenicSpotUserFootprintService {


    int addSpotUserFootPrint(SysScenicSpotUserFootprint sysScenicSpotUserFootprint);


    List<SysScenicSpotUserFootprint> getSpotUserFootPrint(String spotId, String uid);


}
