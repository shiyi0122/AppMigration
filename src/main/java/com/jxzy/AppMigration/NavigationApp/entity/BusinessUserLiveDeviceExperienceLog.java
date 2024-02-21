package com.jxzy.AppMigration.NavigationApp.entity;

import lombok.Data;

/**
 * @Author zhang
 * @Date 2023/5/18 13:21
 */
@Data
public class BusinessUserLiveDeviceExperienceLog {

    private Long id;

    private String robotCode;

    private String userId;

    private String watchedDuration;

    private String recordingTime;

    private String createTime;

    private String updateTime;

    private String dataSide;
}
