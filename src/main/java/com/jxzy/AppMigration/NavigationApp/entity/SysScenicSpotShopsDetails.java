package com.jxzy.AppMigration.NavigationApp.entity;

import lombok.Data;

@Data
public class SysScenicSpotShopsDetails {
    private Long detailsId;

    private Long typeId;

    private String productName;

    private String productPicUrl;

    private String createTime;

    private String updateTime;

    private String state;

    //表中无此字段

    //商铺id
    private String shopsId;
    //类型名称
    private String typeName ;
    //商品类型id
    private Long commodityId;


}