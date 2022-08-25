package com.jxzy.AppMigration.NavigationApp.Service;

import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotUserStop;

import java.util.List;

/**
 * @Author zhang
 * @Date 2022/8/17 11:08
 */
public interface SysScenicSpotUserStopService {

    int addSpotUserStop(SysScenicSpotUserStop sysScenicSpotUserStop);

    List<SysScenicSpotUserStop> getSpotUserStop(String uid, String spotId);

}
