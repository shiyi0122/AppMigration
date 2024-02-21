package com.jxzy.AppMigration.NavigationApp.dao;

import com.jxzy.AppMigration.NavigationApp.entity.Excel.SysScenicReommendedRoute;
import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotRecommendedRoute;


import java.util.List;
import java.util.Map;

public interface SysScenicSpotRecommendedRouteMapper {
    int deleteByPrimaryKey(Long routeId);

    int insert(SysScenicSpotRecommendedRoute record);

    int insertSelective(SysScenicSpotRecommendedRoute record);

    SysScenicSpotRecommendedRoute selectByPrimaryKey(Long routeId);

    int updateByPrimaryKeySelective(SysScenicSpotRecommendedRoute record);

    int updateByPrimaryKey(SysScenicSpotRecommendedRoute record);

    /**
     * 查询景区景点路线列表
     * @param: search
     * @description: TODO
     * @author: qushaobei
     * @date: 2021/11/4 0004
     */
    List<SysScenicSpotRecommendedRoute> queryScenicSpotRecommendedRouteList(Map<String, Object> search);

    /**
     * 查询热门线路
     * 张
     * @param search
     * @return
     */
    List<SysScenicSpotRecommendedRoute> searchRouteList(Map<String, Object> search);


    /**
     * 获取数据总条数
     * 张
     * @param search
     * @return
     */
    Integer selectTotals(Map<String, Object> search);

    /**
     * 全部线路
     * @param search
     * @return
     */
    List<SysScenicSpotRecommendedRoute> getRouteInBroadcastAll(Map<String, Object> search);

    int addHotRouteInBroadcast(Long id);

    /**
     * 查询线路列表中有某个坐标的的线路列表
     * @param broadcastGps
     * @param spotId
     * @return
     */
    List<SysScenicSpotRecommendedRoute> selectByRouteGps(Long spotId,String broadcastGps);

    /**
     * 后台管理——经典线路查询
     * @param search
     * @return
     */
    List<SysScenicSpotRecommendedRoute> getRecommendedRouteList(Map<String, String> search);


    List<SysScenicReommendedRoute> uploadExcelRecommendedRoute(Map<String, String> search);

    SysScenicSpotRecommendedRoute getSpotIdAndSpotRecommendedName(Long scenicSpotId, String routeName);

}