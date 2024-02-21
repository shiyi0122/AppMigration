package com.jxzy.AppMigration.NavigationApp.entity;

import lombok.Data;

/**
 * @Author zhang
 * @Date 2023/1/17 16:51
 */
@Data
public class SysSpotDynamicBanner {

    private Long id;

    private Long spotDynamicId;

    private String spotDynamicBannerUrl;

    private String createTime;

    private String updateTime;
}
