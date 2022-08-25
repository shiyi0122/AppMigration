package com.jxzy.AppMigration.NavigationApp.dao;

import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotRecommendedRoute;

import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotRouteInBroadcast;

import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2022/8/9 16:32
 */
public interface SysScenicSpotRouteInBroadcastMapper {
    List<SysScenicSpotRecommendedRoute> getRouteInBroadcastList(Map<String, Object> search);


    /**
     * 获取热门线路中的路过的景点与数量
     * @param routeId
     * @return
     */
    List<SysScenicSpotRouteInBroadcast> selectSpotRouteIdByList(Long routeId);

}
