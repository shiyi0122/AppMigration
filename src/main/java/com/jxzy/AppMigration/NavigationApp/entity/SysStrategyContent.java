package com.jxzy.AppMigration.NavigationApp.entity;

import lombok.Data;

/**
 * @Author zhang
 * @Date 2023/1/18 10:29
 */
@Data
public class SysStrategyContent {

    private Long id;

    private Long sysStrategyId;

    private String title;

    private String content;

    private String pictureUrl;

    private String createTime;

    private String updateTime;

    private String sort ;

}
