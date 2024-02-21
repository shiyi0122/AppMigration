package com.jxzy.AppMigration.NavigationApp.entity;

import lombok.Data;

/**
 * @Author zhang
 * @Date 2023/1/9 15:52
 */
@Data
public class SysNavigation {

    private Long id ;

    private String navigationName;

    private String navigationPicUrl;

    private String createTime;

    private String updateTime;

    private String sort;
}
