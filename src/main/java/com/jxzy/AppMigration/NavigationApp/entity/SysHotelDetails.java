package com.jxzy.AppMigration.NavigationApp.entity;

import lombok.Data;

/**
 * @Author zhang
 * @Date 2023/4/24 10:40
 */

@Data
public class SysHotelDetails {

    private Long id;

    private Long hotelId;

    private String hotelDetailsName;

    private String price;

    private Long recommendNumber;

    private String coverPic;

    private String createTime;

    private String updateTime;

}
