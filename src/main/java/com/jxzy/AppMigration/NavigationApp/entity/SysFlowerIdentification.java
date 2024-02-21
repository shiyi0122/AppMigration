package com.jxzy.AppMigration.NavigationApp.entity;

import lombok.Data;

/**
 * @Author zhang
 * @Date 2023/1/19 16:39
 */

@Data
public class SysFlowerIdentification {

    private Long id;
    private String category;

    private String scientificName;
    private String introduce;

    private String createTime;

    private String updateTime;

    private String name;
}
