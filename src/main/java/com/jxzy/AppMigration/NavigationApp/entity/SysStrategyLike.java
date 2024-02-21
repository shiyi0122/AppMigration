package com.jxzy.AppMigration.NavigationApp.entity;

import lombok.Data;

/**
 * @Author zhang
 * @Date 2023/1/12 16:44
 * 游记，攻略，广场 ，评论实体类
 */
@Data
public class SysStrategyLike {

    private Long id;

    private Long strategyId;

    private Long userId;

    private String createTime;

    private String updateTime;

    private String type;

    private Long strategyUid;
}
