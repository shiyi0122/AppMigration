package com.jxzy.AppMigration.NavigationApp.dao;

import com.jxzy.AppMigration.NavigationApp.entity.SysEntertainmentFabulous;

/**
 * @Author zhang
 * @Date 2023/4/24 15:35
 */
public interface SysEntertainmentFabulousMapper {
    int insert(SysEntertainmentFabulous sysEntertainmentFabulous);

    int deleteById(Long id, String uid);


}
