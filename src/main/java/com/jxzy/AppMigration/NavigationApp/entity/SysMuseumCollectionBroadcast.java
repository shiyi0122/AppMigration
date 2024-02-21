package com.jxzy.AppMigration.NavigationApp.entity;

import lombok.Data;

/**
 * @Author zhang
 * @Date 2023/6/21 13:28
 */
@Data
public class SysMuseumCollectionBroadcast {

    private Long id ;

    private Long museumId;

    private Long museumCollectionId;

    private String museumCollectionContent;

    private String museumMediumResourceUrl;

    private String createTime;

    private String updateTime;


}
