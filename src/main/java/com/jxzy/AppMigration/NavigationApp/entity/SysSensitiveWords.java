package com.jxzy.AppMigration.NavigationApp.entity;

import lombok.Data;

/**
 * @Author zhang
 * @Date 2023/1/14 15:53
 */
@Data
public class SysSensitiveWords {

    private Long id;

    private String sensitiveWords;

    private String createTime;

    private String updateTime;
}
