package com.jxzy.AppMigration.NavigationApp.entity;

import lombok.Data;

import java.util.List;

/**
 * @Author zhang
 * @Date 2023/1/12 19:08
 * 动态
 *
 */
@Data
public class SysSpotDynamic {

    private Long id;

    private String title;

    private String introduce;

    private String ascriptionSpotId;

    private String content;

    private String likeNumber;

    private String browseNumber;

    private String coverPic;

    private String createTime;

    private String updateTime;

    private Long province;

    private Long city;

    private Long area;

    private String rotationChart;

    private String scenicSpotName;

    private String contentSplice;


    private List<SysSpotDynamicBanner> bannerList;

    private List<SysSpotDynamicContent> contentList;
}
