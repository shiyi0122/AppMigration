package com.jxzy.AppMigration.NavigationApp.entity;

import lombok.Data;

/**
 * @Author zhang
 * @Date 2023/1/15 14:09
 * 相册
 */
@Data
public class SysUserAlbum {

    private Long id ;

    private String pictureUrls;

    private String userId;

    private String date ;

    private String createTime;

    private String updateTime;



}
