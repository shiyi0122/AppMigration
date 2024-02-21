package com.jxzy.AppMigration.NavigationApp.dao;

import com.jxzy.AppMigration.NavigationApp.entity.SysFeaturedFoodFabulous;

/**
 * @Author zhang
 * @Date 2023/1/10 16:47
 */
public interface SysFeaturedFoodFabulousMapper {
    int  insert(SysFeaturedFoodFabulous sysFeaturedFoodFabulous);


    int delete(Long id, String type, String uid);

    Integer getUidAndShopIdAndTypeByFabulous(String uid, int type, Long id);

}
