package com.jxzy.AppMigration.NavigationApp.Service;

import com.jxzy.AppMigration.NavigationApp.entity.Excel.SysScenicReommendedRoute;
import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotBroadcast;
import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotRecommendedRoute;
import com.jxzy.AppMigration.NavigationApp.util.PageDataResult;

import java.util.List;
import java.util.Map;

public interface SysScenicSpotRecommendedRouteService {
    /**
     * 查询景区景点路线列表
     * @param: pageNum
     * @param: pageSize
     * @param: search
     * @description: TODO
     * @author: qushaobei
     * @date: 2021/11/4 0004
     */
    List<SysScenicSpotRecommendedRoute> queryScenicSpotRecommendedRouteList(int pageNum, int pageSize, Map<String, Object> search);

    /**
     * 后台管理——伴游线路列表
     * @param search
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageDataResult getRecommendedRouteList(Map<String, String> search, Integer pageNum, Integer pageSize);


    /**
     *后台管理——添加伴游线路
     * @param sysScenicSpotRecommendedRoute
     * @return
     */
    int addRecommendedRoute(SysScenicSpotRecommendedRoute sysScenicSpotRecommendedRoute);

    /**
     *后台管理——修改伴游线路
     * @param sysScenicSpotRecommendedRoute
     * @return
     */
    int exitRecommendedRoute(SysScenicSpotRecommendedRoute sysScenicSpotRecommendedRoute);
    /**
     *删除伴游线路
     * @param
     * @return
     */
    int delRecommendedRoute(Long routeId);

    /**
     * 后台管理——经典路线添加线路中的景点
     * @param routeId
     * @param broadcastId
     * @return
     */
    int addRouteInBroadcast(String routeId, String broadcastId);

    /**
     * 后台管理——查询线路中的景点
     * @param id
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageDataResult getRouteInBroadcast(Long id, Integer pageNum, Integer pageSize);


    /**
     *后台管理——删除线路中的景点
     * @param id
     * @return
     */
    int delRouteInBroadcast(Long id);

    /**
     * 后台管理——获取景区下的所有景点
     * @param spotId
     * @return
     */
    List<SysScenicSpotBroadcast> getSpotLowerBroadcast(Long spotId);

    /**
     * 后台管理——经典线路状态修改
     * @param routeId
     * @param routeState
     * @return
     */
    int exitRecommendedRouteState(String routeId, String routeState);

    /**
     * 后台管理--线路导出
     * @param search
     * @return
     */
    List<SysScenicReommendedRoute> uploadExcelRecommendedRoute(Map<String, String> search);

    /**
     * 根据景区id和线路名称查询线路
     * @param scenicSpotId
     * @param routeName
     * @return
     */
    SysScenicSpotRecommendedRoute getSpotIdAndSpotRecommendedName(Long scenicSpotId, String routeName);

    /**
     * 定时获取线路数据添加
     * @param sysScenicSpotRecommendedRoute
     * @return
     */
    int insert(SysScenicSpotRecommendedRoute sysScenicSpotRecommendedRoute);


    /**
     * 根据id查询
     * @param routeId
     * @return
     */
    SysScenicSpotRecommendedRoute getRecommendedRouteId(Long routeId);

}
