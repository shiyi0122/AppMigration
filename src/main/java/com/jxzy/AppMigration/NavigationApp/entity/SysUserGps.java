package com.jxzy.AppMigration.NavigationApp.entity;

import lombok.Data;

/**
 * @Author zhang
 * @Date 2023/3/11 11:20
 */
@Data
public class SysUserGps {

    //经度
    private String longitude;
    //纬度
    private String latitude;
    //强度
    private String intensity;

    //84坐标
    private String longitudeGps;
    //84坐标
    private String latitudeGps;
    //景区id
    private Long scenicSpotId;

}
