package com.jxzy.AppMigration.NavigationApp.dao;

import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotBanner;

import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2022/7/30 13:50
 */
public interface SysScenicSpotBannerMapper {


    List<SysScenicSpotBanner> getScenicSpotBanner(Map<String, Object> search);


    int addScenicSpotBanner(SysScenicSpotBanner sysScenicSpotBanner);

    int editScenicSpotBanner(SysScenicSpotBanner sysScenicSpotBanner);


}
