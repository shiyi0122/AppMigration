package com.jxzy.AppMigration.NavigationApp.entity;

import lombok.Data;

import java.util.List;

@Data
public class SysScenicSpot {
    
    private Long scenicSpotId;

    private String scenicSpotName;

    private String scenicSpotContact;

    private String scenicSpotPhone;

    private String scenicSpotEmail;

    private String scenicSpotAddres;

    private String scenicSpotPostalCode;

    private String scenicSpotRobotTotal;

    private String scenicSpotStatus;

    private String scenicSpotOpenword;

    private String scenicSpotCloseword;

    private String scenicSpotBeyondPrice;

    private String scenicSpotWeekendPrice;

    private String scenicSpotNormalPrice;

    private String scenicSpotWeekendRentPrice;

    private String scenicSpotNormalRentPrice;

    private String scenicSpotWeekendTime;

    private String scenicSpotNormalTime;

    private String scenicSpotRemainingTime;

    private String scenicSpotRentTime;

    private String coordinateRange;

    private String scenicSpotDeposit;

    private String normalCappedPrice;

    private String weekendCappedPrice;

    private String randomBroadcastTime;

    private String robotWakeupWords;

    private String scenicSpotTheServer;

    private String createDate;

    private String updateDate;

    private Long scenicSpotFid;

    private String scenicSpotFrequency;

    private String scenicSpotFenceTime;

    private String scenicSpotForbiddenZoneTime;

    private String testStartTime;

    private String trialOperationsTime;

    private String formalOperationTime;

    private String stopOperationTime;

    private Long companyId;

    private String pauseState;

    private String workTime;

    private String closingTime;

    private String pauseBroadcast;

    private String workBroadcast;

    private String closingBroadcast;

    private String lampOpeningTime;

    private String lampClosingTime;

    private String lampLightingTime;

    private String freeTimeSetting;

    private String giftTimeSetting;

    private String giftUsageSetting;

    private String scenicSpotImgUtl;

    private String scneicSpotImgUrl;

    private String total;

    private String broadcastGps;

    private Long heat;

    private List<SysScenicSpotHeat> heatList;

    //景区星级
    private Long startLevel;
    //景区介绍
    private String  introduction;
    //距离
    private Double distance ;
    //景点数量
    private Integer scenicSpotBroadcastCount;
    //景区轮播图
    private String url;





}