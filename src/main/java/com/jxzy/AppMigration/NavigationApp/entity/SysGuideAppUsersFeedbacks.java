package com.jxzy.AppMigration.NavigationApp.entity;

import lombok.Data;

@Data
public class SysGuideAppUsersFeedbacks {
    private Long id;

    private String content;

    private String urlPic;

    private Long userId;

    private String createTime;

    private String updateTime;

    private String userName;

    private String status;

    private Integer picCount;

    private String replyContent;

}