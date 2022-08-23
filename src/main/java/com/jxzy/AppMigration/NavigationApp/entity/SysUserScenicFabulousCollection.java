package com.jxzy.AppMigration.NavigationApp.entity;

import lombok.Data;

import java.util.List;

@Data
public class SysUserScenicFabulousCollection {
    private Long id;

    private Long userId;

    private Long scenicDistrictId;

    private String fabulous;

    private String collection;

    private String createTime;

    private String updateTime;

    private List<SysScenicSpotBroadcast> sysScenicSpotBroadcasts;

}