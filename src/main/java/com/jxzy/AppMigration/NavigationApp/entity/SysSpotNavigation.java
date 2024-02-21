package com.jxzy.AppMigration.NavigationApp.entity;

import lombok.Data;

/**
 * @Author zhang
 * @Date 2023/1/13 9:42
 */
@Data
public class SysSpotNavigation {

    private Long id ;

    private String spotNavigationName;

    private String spotNavigationPicUrl;

    private String createTime;

    private String updateTime;

    private String sort;

}
