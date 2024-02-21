package com.jxzy.AppMigration.NavigationApp.entity;

import lombok.Data;

/**
 * @Author zhang
 * @Date 2023/1/13 15:00
 * 关于我们
 */
@Data
public class SysAboutUs {

    private Long id;

    private String title;

    private String content;

    private String createTime;

    private String updateTime;

    private String typeId;

    private Long appSubversionId;

}
