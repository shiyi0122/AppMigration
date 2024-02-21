package com.jxzy.AppMigration.NavigationApp.entity;

import lombok.Data;

/**
 * @Author zhang
 * @Date 2023/5/16 19:18
 */
@Data
public class BusinessUserLiveDeviceLog {

    private Long id ;

    private Long userId;

    private String robotCode;

    private String ifPay;

    private String createTime;

    private String updateTime;

    private String paymentTime;

    private String exitTime;

    private String watchedDuration;

    private String dataSide;

    private String scenicSpotName;


}
