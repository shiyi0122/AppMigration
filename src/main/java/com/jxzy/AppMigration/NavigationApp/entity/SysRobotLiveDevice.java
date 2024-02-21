package com.jxzy.AppMigration.NavigationApp.entity;

import lombok.Data;

/**
 * @Author zhang
 * @Date 2023/5/11 13:40
 */
@Data
public class SysRobotLiveDevice {

    private Long liveId;

    private Long scenicSpotId;

    private Long robotId;

    private String deviceId;

    private String robotCode;

    private String deviceNumber;

    private String createTime;

    private String updateTime;

    private String scenicSpotName;

    private String city;

    private String scenicSpotPic;

    private String leftCamera;

    private String rightCamera;

    private String frontCamera;

    private String afterCamera;

    private String upperCamera;

    private String ifOnLine;

    private String robotGpsSmallApp;


}
