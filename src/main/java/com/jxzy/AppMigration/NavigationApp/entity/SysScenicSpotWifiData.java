package com.jxzy.AppMigration.NavigationApp.entity;

import lombok.Data;

@Data
public class SysScenicSpotWifiData {
    private Long wifiId;

    private String mac;

    private String rssi;

    private String rssiOne;

    private String rssiTwo;

    private String rssiThree;

    private String tmc;

    private String router;

    private String ranges;

    private String createDate;

    private String updateDate;

}