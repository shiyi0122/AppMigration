package com.jxzy.AppMigration.NavigationApp.entity;

import lombok.Data;

@Data
public class SysGuideAppUsersFeedback {
    private Long feedbackId;

    private Long appUserId;

    private String feedbackContent;

    private String createDate;

    private String updateDate;

}