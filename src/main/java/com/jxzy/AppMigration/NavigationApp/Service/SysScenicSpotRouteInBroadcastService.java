package com.jxzy.AppMigration.NavigationApp.Service;

import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotBroadcast;
import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotRecommendedRoute;
import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotRouteInBroadcast;
import com.jxzy.AppMigration.NavigationApp.util.PageDataResult;

import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2022/8/9 16:29
 */
public interface SysScenicSpotRouteInBroadcastService {

    PageDataResult getRouteInBroadcastList(Map<String, Object> search);

    PageDataResult getRouteInBroadcastAll(Map<String, Object> search);


    int addHotRouteInBroadcast(Long id);


    List<SysScenicSpotBroadcast> spotPanorama(String spotId, String lat, String lng);


    List<SysScenicSpotBroadcast>  lineDetails(String spotId,String id,String lat, String lng);


    List<SysScenicSpotRecommendedRoute> recommendLine(String spotId,String id);

}
