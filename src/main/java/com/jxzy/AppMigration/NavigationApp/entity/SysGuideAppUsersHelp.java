package com.jxzy.AppMigration.NavigationApp.entity;

import com.jxzy.AppMigration.NavigationApp.entity.base.BaseDTO;
import lombok.Data;

@Data
public class SysGuideAppUsersHelp extends BaseDTO {
    private Long helpId;

    private String helpTitle;

    private String createDate;

    private String updateDate;

    private String helpContent;
}