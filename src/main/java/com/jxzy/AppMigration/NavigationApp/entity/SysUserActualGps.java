package com.jxzy.AppMigration.NavigationApp.entity;

import lombok.Data;

/**
 * @Author zhang
 * @Date 2023/3/11 15:25
 */
@Data
public class SysUserActualGps {

    private Long id;

    //用户id
    private Long  userId;

    //84坐标
    private String  userCoordinatesGps;
    //百度坐标
    private String  userCoordinatesBaidu;
    //高德坐标
    private String  userCoordinatesGaode;
    //省id
    private Long provincesId;
    //市id
    private Long  cityId;
    //区id
    private Long areaId;
    //景区id
    private Long spotId;
    //景点id
    private Long  broadcastId;

    private String createDate;

    private String updateDate;
    //省名称
    private String provincesName;
    //市名称
    private String cityName;
//区名称
    private String areaName;

}
