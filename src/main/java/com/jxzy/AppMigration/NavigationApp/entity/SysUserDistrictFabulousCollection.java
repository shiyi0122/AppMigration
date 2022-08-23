package com.jxzy.AppMigration.NavigationApp.entity;

import lombok.Data;

import java.util.List;

@Data
public class SysUserDistrictFabulousCollection {
    private Long id;

    private Long userId;

    private Long scenicSpotId;

    private String fabulous;

    private String collection;

    private String createTime;

    private String updateTime;

    private List<SysScenicSpot> sysScenicSpotList;

}