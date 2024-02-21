package com.jxzy.AppMigration.NavigationApp.entity;

import lombok.Data;

/**
 * @Author zhang
 * @Date 2023/4/3 19:22
 */
@Data
public class SysScenicSpotRecommend {

    private Long id;

    private Long recommendSpotId;

    private String sort;

    private String createTime;

    private String updateTime;

    private String scenicSpotName;

    private String scneicSpotImgUrl;


}
