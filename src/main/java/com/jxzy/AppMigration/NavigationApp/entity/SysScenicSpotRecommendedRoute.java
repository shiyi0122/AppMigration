package com.jxzy.AppMigration.NavigationApp.entity;

import lombok.Data;

import java.util.List;

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

    private String totalKm;

    private String useTime;

    private String heat;

    private String pictureUrl;


    //表中没有的字段数据

    //景点数量
    private String broadcastCount;
    //语音播报数量
    private String voiceCount;
    //路线中各个景点坐标
    private List<String> routeInBroadcastList;

}