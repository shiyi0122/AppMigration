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

    private String introduction;

    private String createTime;

    private String updateTime;

    private String shopsState;

    private String shopsPinyinName;

    private String shopsGpsBaidu;

    private String shopsGpsGaode;

    private String shopsRange;

    private String businessHours;

    private String productIntroduction;

    private List<SysScenicSpotShopsType> sysScenicSpotShopsType;

    private Double distance;

    //表中无此字段
    private String spotName;

}