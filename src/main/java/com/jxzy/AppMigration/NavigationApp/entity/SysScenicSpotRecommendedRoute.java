package com.jxzy.AppMigration.NavigationApp.entity;

import lombok.Data;

@Data
public class SysScenicSpotRecommendedRoute {
    private Long routeId;

    private Long scenicSpotId;

    private String routeName;

    private String routeNamePinYin;

    private String routeIntroduce;

    private String routeGps;

    private String routeGpsBaiDu;

    private String createDate;

    private String updateDate;

}