package com.jxzy.AppMigration.NavigationApp.entity;

import lombok.Data;

/**
 * @Author zhang
 * @Date 2022/8/3 18:20
 */
@Data
public class SysScenicRankingList {

    private Long  id ;

    private String content;

    private Long spotId;

    private String type;

    private String createTime;

    private String updateTime;
}
