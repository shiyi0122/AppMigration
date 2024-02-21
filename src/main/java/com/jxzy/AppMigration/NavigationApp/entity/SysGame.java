package com.jxzy.AppMigration.NavigationApp.entity;

import lombok.Data;

/**
 * @Author zhang
 * @Date 2023/1/11 17:03
 * 娱乐项目
 */
@Data
public class SysGame {

    private Long id ;

    private String  gameName;

    private String gamePinyin;

    private String gameGpsBaiDu;

    private String gameGpsGaoDe;

    private String coordinateRadius;

    private String gameGps;

    private String price;

    private Long ascriptionSpotId;

    private String coverPic;

    private String createTime;

    private String updateTime;

    private String detailsPic;

    private String province;

    private String city;

    private String area;

    private String businessHours;

    private String recommendNumber;

    private String address;

    private String broadcastContent;

    private String isPeriphery;

    private String provinceName;

    private String cityName;

    private String areaName;

    //以下字段数据表中没有
    private double distance;

    private String scenicSpotName;


}
