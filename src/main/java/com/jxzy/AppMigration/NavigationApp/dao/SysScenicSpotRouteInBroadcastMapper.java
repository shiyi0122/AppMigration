package com.jxzy.AppMigration.NavigationApp.dao;

import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotBroadcast;
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

    /**
     * 根据景区id和线路id，查询景点
     * @param search
     * @return
     */
    List<SysScenicSpotRouteInBroadcast> getSpotIdAndRouteIdByList(Map<String, Object> search);


    /**
     * 后台管理—— 删除经典线路中的各个景点
     * @param routeId
     * @return
     */
    int deleteByRouteId(Long routeId);

    /**
     * 后台管理-- 添加线路关联景点
     * @param sysScenicSpotRouteInBroadcast
     * @return
     */
    int insertService(SysScenicSpotRouteInBroadcast sysScenicSpotRouteInBroadcast);
    /**
     *
     * 后台管理—— 根据id删除线路关联景点
     * @param
     */

    int deleteById(Long id);

    List<SysScenicSpotRouteInBroadcast> selectSpotRouteId(Long routeId);

}
