package com.jxzy.AppMigration.NavigationApp.dao;

import com.jxzy.AppMigration.NavigationApp.entity.SysGoodThingsFabulous;

/**
 * @Author zhang
 * @Date 2023/1/10 19:47
 */
public interface SysGoodThingsFabulousMapper {
    Integer getUidAndShopIdAndTypeByFabulous(String uid, int type, Long id);

    int insert(SysGoodThingsFabulous sysGoodThingsFabulous);


    int  delete(Long id, String type, String uid);

}
