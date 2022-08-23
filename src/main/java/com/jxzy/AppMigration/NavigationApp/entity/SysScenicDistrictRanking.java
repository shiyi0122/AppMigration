package com.jxzy.AppMigration.NavigationApp.entity;

import lombok.Data;

@Data
public class SysScenicDistrictRanking {
    private Long id;

    private Long broadcastId;

    private Integer sameDay;

    private Integer total;

    private String type;

    private String createTime;

    private String updateTime;
}