package com.jxzy.AppMigration.NavigationApp.entity;

import lombok.Data;

/**
 * @Author zhang
 * @Date 2023/1/17 19:46
 */
@Data
public class SysSpotDynamicContent {

    private Long id;

    private Long spotDynamicId;

    private String content;

    private String priceUrl;

    private String createTime;
}
