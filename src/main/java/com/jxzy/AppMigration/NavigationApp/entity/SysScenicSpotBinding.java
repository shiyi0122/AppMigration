package com.jxzy.AppMigration.NavigationApp.entity;

import lombok.Data;

import java.util.List;

@Data
public class SysScenicSpotBinding {
    private Long scenicSpotFid;

    private String scenicSpotFname;

    private Long scenicSpotPid;

    private Integer scenicSpotType;

    private String cityLabel;

    private String cityPic;

    private List<SysScenicSpot> sysScenicSpotList;
}