package com.jxzy.AppMigration.NavigationApp.entity;

import lombok.Data;

@Data
public class SysGuideAppUsers {
    private Long userId;

    private String userPhone;

    private String longinTokenId;

    private String userClientGtId;

    private String phoneSign;

    private String createDate;

    private String updateDate;
}