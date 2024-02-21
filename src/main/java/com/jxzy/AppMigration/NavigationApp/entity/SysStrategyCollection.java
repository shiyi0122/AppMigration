package com.jxzy.AppMigration.NavigationApp.entity;

import lombok.Data;

/**
 * @Author zhang
 * @Date 2023/1/12 16:39
 * 游记，攻略，广场 ，收藏实体类
 *
 */
@Data
public class SysStrategyCollection {

    //id
    private Long id;
    //文章id
    private Long strategyId;

    //用户id
    private Long userId;

    private String createTime;

    private String updateTime;
    //类型
    private String type;

    private String state;
    //文章作者id
    private Long strategyUid;
}
