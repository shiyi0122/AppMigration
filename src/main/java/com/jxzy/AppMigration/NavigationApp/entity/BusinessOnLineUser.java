package com.jxzy.AppMigration.NavigationApp.entity;

import lombok.Data;

/**
 * @Author zhang
 * @Date 2023/5/19 14:03
 */
@Data
public class BusinessOnLineUser {

    private Long id;

    private String robotCode;

    private Long userId;

    private String type;

    private String createTime;

    private String updateTime;

}
