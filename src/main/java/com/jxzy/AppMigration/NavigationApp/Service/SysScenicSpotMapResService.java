package com.jxzy.AppMigration.NavigationApp.Service;

import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotMapRes;

/**
 * @Author zhang
 * 地图相关
 * @Date 2022/8/29 18:36
 */
public interface SysScenicSpotMapResService {


    /**
     * 下载地图
     * @param spotId
     * @return
     */
    SysScenicSpotMapRes getScenicSpotMapRes(Long spotId);


}
