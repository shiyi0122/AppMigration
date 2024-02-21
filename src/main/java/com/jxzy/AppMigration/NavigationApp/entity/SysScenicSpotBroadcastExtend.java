package com.jxzy.AppMigration.NavigationApp.entity;

import lombok.Data;

import java.util.List;


@Data
public class SysScenicSpotBroadcastExtend {
    private Long broadcastResId;

    private Long broadcastId;

    private String mediaResourceUrl;

    private String mediaType;

    private String fabulous;

    private String collection;

    private String createDate;

    private String updateDate;

    private String  scenicSpotName;

    private String broadcastName;

    private String introduce;

    private List<SysScenicSpotBroadcastAdmissionFee> admissionFeeList;

}