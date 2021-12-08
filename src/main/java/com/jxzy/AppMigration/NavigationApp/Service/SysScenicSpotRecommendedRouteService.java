package com.jxzy.AppMigration.NavigationApp.Service;

import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotRecommendedRoute;

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
}
