package com.jxzy.AppMigration.NavigationApp.entity;

import lombok.Data;

@Data
public class SysGuideAppNews {
    private Long guideId;

    private String guideUserId;

    private String guideTitle;

    private String guideState;

    private String createDate;

    private String updateDate;

    private String guideContent;

    private String plannedTime;

    private String sendStatus;

    private String messageType;

    private String ifRead;

    private  String[] userList;

    private String newsType;

    private String newsLock;
}