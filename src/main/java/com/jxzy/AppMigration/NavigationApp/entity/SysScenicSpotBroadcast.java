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

    private List<SysScenicSpotBroadcastExtendWithBLOBs> SysScenicSpotBroadcastExtend;

    private List<SysScenicSpot> spotList;

    private List<SysScenicDistrictRanking> rankings;

    private String scenicSpotAddres;


}