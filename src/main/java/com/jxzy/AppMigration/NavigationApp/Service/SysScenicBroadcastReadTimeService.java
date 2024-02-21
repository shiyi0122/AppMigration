package com.jxzy.AppMigration.NavigationApp.Service;

import com.jxzy.AppMigration.NavigationApp.entity.SysScenicBroadcastReadTime;

/**
 * @Author zhang
 * @Date 2022/10/1 8:53
 */
public interface SysScenicBroadcastReadTimeService {
    int addBroadcastReadTime(SysScenicBroadcastReadTime sysScenicBroadcastReadTime);


    int delBroadcastReadTime(Long userId, Long broadcastId);

}
