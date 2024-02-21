package com.jxzy.AppMigration.NavigationApp.entity;

import lombok.Data;

@Data
public class SysScenicSpotParking {
    private Long parkingId;


    private String parkingName;

    private String parkingContent;

    private Long parkingScenicSpotId;

    private String parkingType;

    private String coordinateType;

    private String createDate;

    private String updateDate;

    private String spotName;

    private String  parkingPinyinName;

    private String parkingRange;

    private String broadcastContent ;
}