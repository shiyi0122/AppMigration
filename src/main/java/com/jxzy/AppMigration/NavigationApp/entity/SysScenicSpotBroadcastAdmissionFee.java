package com.jxzy.AppMigration.NavigationApp.entity;

import lombok.Data;

/**
 * @Author zhang
 * @Date 2023/5/4 13:52
 */
@Data
public class SysScenicSpotBroadcastAdmissionFee {

    private Long id;

    private Long spotId;

    private Long  broadcastId;

    private String admissionFeeName;

    private String admissionFeePrice;

    private String admissionFeeIllustrate;

    private String effectiveDays;

    private String createTime;

    private String updateTime;

    private String spotName;

    private String broadcastName;
}
