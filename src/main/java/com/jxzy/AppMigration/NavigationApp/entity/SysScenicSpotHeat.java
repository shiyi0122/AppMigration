package com.jxzy.AppMigration.NavigationApp.entity;

import lombok.Data;

@Data
public class SysScenicSpotHeat {
    private Long id;

    private Long scenicSpotId;

    private int sameDay;

    private int total;

    private String type;

    private String createTime;

    private String updateTime;

}