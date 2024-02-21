package com.jxzy.AppMigration.NavigationApp.entity;

import lombok.Data;

/**
 * @Author zhang
 * @Date 2023/1/10 9:44
 * 特色美食
 */
@Data
public class SysFeaturedFood {

    private Long id;

    private String foodShopId;

    private String foodName;

    private String price;

    private Long recommendNumber;

    private String  coverPic;

    private String createTime;

    private String updateTime;

    //下面字段数据表中没有
    private String isFabulous;
}
