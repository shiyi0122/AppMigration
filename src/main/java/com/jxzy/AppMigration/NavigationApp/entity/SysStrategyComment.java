package com.jxzy.AppMigration.NavigationApp.entity;

import lombok.Data;

/**
 * @Author zhang
 * @Date 2023/1/12 16:42
 * 游记，攻略，广场 ，评论实体类
 *
 */

@Data
public class SysStrategyComment {
    private Long id;

    private Long strategyId;

    private Long userId;

    private String createTime;

    private String updateTime;

    private String type;

    private String state;
}
