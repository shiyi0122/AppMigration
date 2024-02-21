package com.jxzy.AppMigration.NavigationApp.entity;

import lombok.Data;

import java.util.List;

@Data
public class SysScenicSpotBinding {
    private Long scenicSpotFid;

    private String scenicSpotFname;

    private Long scenicSpotPid;

    private Long scenicSpotSid;

    private Long scenicSpotQid;

    private Integer scenicSpotType;

    private String cityLabel;

    private String cityPic;

    private String isHotCity;

    private String isDirectly;



    private String scenicSpotSname;

    private String scenicSpotQname;

    private String scenicSpotPname;


    private List<SysScenicSpotBinding> sysScenicBindingList;

    private List<SysScenicSpot> sysScenicSpotList;

}