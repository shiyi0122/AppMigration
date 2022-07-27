package com.jxzy.AppMigration.NavigationApp.dao;

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
}