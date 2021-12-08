package com.jxzy.AppMigration.NavigationApp.entity;

import lombok.Data;

@Data
public class SysGuideAppNews {
    private Long guideId;

    private Long guideUserId;

    private String guideTitle;

    private String guideState;

    private String createDate;

    private String updateDate;

    private String guideContent;
}