package com.jxzy.AppMigration.NavigationApp.entity;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class SysScenicSpotBroadcast {
    private Long broadcastId;

    private Long scenicSpotId;

    private String broadcastGps;

    private String broadcastGpsBaiDu;

    private String scenicSpotRange;

    private String broadcastName;

    private String pinYinName;

    private String introductionTypes;

    private String navigationType;

    private String broadcastPriority;

    private String sortType;

    private String createDate;

    private String updateDate;


    private String heat;

    private List<SysScenicSpotBroadcastExtendWithBLOBs> SysScenicSpotBroadcastExtend;

    private List<SysScenicSpot> spotList;

    private List<SysScenicDistrictRanking> rankings;

    private String scenicSpotAddres;

    private String pictureUrl;

    // 新添加

    //是否特色景点
    private String isFeature;
    //景点介绍
    private String introduce;
    //承受人数
    private Long bearPeople;
    //距离
    private  double distance;
    //景点地址
    private String spotBroadcastAddress;



}