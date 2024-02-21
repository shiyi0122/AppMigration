package com.jxzy.AppMigration.NavigationApp.entity;

import lombok.Data;

/**
 * @Author zhang
 * @Date 2023/3/20 17:20
 */
@Data
public class SysAppVersion {

    private Long  id ;

    private String appUrl;

    private String appType;

    private String appSize;

    private String appVersion;

    private String appDescription;

    private String updateType;

    private String packageType;

    private String startTime;

    private String endTime;

    private String createDate;

    private String updateDate;

    private String spotType ;

}
