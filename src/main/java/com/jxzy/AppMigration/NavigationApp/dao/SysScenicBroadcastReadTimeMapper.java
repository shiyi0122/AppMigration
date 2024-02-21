package com.jxzy.AppMigration.NavigationApp.dao;

import com.jxzy.AppMigration.NavigationApp.entity.SysScenicBroadcastReadTime;

/**
 * @Author zhang
 * @Date 2022/10/1 8:54
 */
public interface SysScenicBroadcastReadTimeMapper {

    int insertSelective(SysScenicBroadcastReadTime sysScenicBroadcastReadTime);

    Integer getBroadcastRealTimePeople(Long broadcastId);

    int delBroadcastReadTime(Long userId, Long broadcastId);

}
