package com.jxzy.AppMigration.NavigationApp.entity;

import lombok.Data;

@Data
public class SysRobotMapRes {
    private Long resId;

    private Long resScenicSpotId;

    private String resUrl;

    private String resType;

    private String resSize;

    private String resVersion;

    private String createDate;

    private String updateDate;

    private String scenicSpotName;
}