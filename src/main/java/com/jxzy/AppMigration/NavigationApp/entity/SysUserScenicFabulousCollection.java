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

    private String userName;

    private List<SysScenicSpotBroadcast> sysScenicSpotBroadcasts;

    private Integer fabulousCount;

    private Integer collectionCount;

    private String  broadcastName;

    private Double distance;

    private String scenicSpotName;

    private String broadcastImg;


}