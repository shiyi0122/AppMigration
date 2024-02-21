package com.jxzy.AppMigration.NavigationApp.entity;

import lombok.Data;

/**
 * @Author zhang
 * @Date 2023/1/10 16:46
 */
@Data
public class SysFeaturedFoodFabulous {

    private Long id;

    private Long featuredFoodId;

    private Long userId;

    private String  createTime;

    private String updateTime;

    private String type;
}
