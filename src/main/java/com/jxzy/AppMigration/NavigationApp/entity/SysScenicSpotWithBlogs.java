package com.jxzy.AppMigration.NavigationApp.entity;

import lombok.Data;

/**
 * @Author zhang
 * @Date 2022/8/27 18:00
 */
@Data
public class SysScenicSpotWithBlogs extends SysScenicSpot {

    private Long hotSearchCount;

    private Long peopleSearchCount;

    private Long welcomeSearchCount;

    private Long collectionSearchCount;

    private Long likeSearchCount;

    private String provinceName;

    private String cityName;

    private String areaName;

}
