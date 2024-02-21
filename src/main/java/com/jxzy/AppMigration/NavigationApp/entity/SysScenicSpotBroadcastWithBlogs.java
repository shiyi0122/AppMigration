package com.jxzy.AppMigration.NavigationApp.entity;

import lombok.Data;

/**
 * @Author zhang
 * @Date 2022/8/28 11:19
 */
@Data
public class SysScenicSpotBroadcastWithBlogs extends SysScenicSpotBroadcast  {

    private Long hotSearchCount;

    private Long peopleSearchCount;

    private Long welcomeSearchCount;

    private Long collectionSearchCount;

    private Long likeSearchCount;

    private double avgTime;
}
