package com.jxzy.AppMigration.NavigationApp.entity;

import lombok.Data;

import java.util.List;

@Data
public class SysScenicSpotShops {
    private Long shopsId;

    private Long scenicSpotId;

    private String shopsName;

    private String shopsPhone;

    private String shopsAddress;

    private String shopsGps;

    private String shopsPic;

    private String createTime;

    private String updateTime;

    private List<SysScenicSpotShopsType> sysScenicSpotShopsType;

}