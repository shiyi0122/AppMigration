package com.jxzy.AppMigration.NavigationApp.entity;

import lombok.Data;

/**
 * @Author zhang
 * @Date 2023/6/20 17:00
 * 头戴式设备
 */
@Data
public class SysHeadMounted {

    private Long id;

    private Long museumId;

    private String headMountedModel;

    private String headMountedCode;

    private String headMountedSimCard;

    private String  headMountedPic;

    private String bluetoothNumber;

    private String usedSpace;

    private String totalSpace;

    private String devicePower;

    private String useState;

    private String createTime;

    private String updateTime;


}
