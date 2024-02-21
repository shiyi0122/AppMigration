package com.jxzy.AppMigration.NavigationApp.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

import java.util.List;

/**
 * @Author zhang
 * @Date 2023/1/10 9:42
 * 特色美食店铺
 */
@Data
public class SysFeaturedFoodShop {

    private Long id;

    private Long spotId;
    @Excel(name = "美食店铺名称",orderNum = "1")
    private String featuredFoodName;
    @Excel(name = "酒店民宿名称拼音",orderNum = "2")
    private String featuredFoodPinyin;
    @Excel(name = "百度坐标",orderNum = "4")
    private String featuredGpsBaiDu;
    @Excel(name = "高德坐标",orderNum = "5")
    private String featuredGpsGaoDe;
    @Excel(name = "84坐标",orderNum = "6")
    private String featuredGps;
    @Excel(name = "均价",orderNum = "7")
    private String price;
    @Excel(name = "手机号",orderNum = "8")
    private String phone;
    @Excel(name = "营业时间",orderNum = "9")
    private String businessHours;

    private Long recommendNumber;

    private String coverPic;

    private String detailsPic;
    @Excel(name = "创建时间",orderNum = "10",importFormat = "yyyy-MM-dd hh:mm:ss")
    private String createTime;

    private String updateTime;

    private Long province;

    private Long city;

    private Long area;
    @Excel(name = "地址",orderNum = "11")
    private String address;
    @Excel(name = "播报内容",orderNum = "12")
    private String broadcastContent;

    private String provinceName;

    private String cityName;

    private String areaName;
    @Excel(name = "坐标半径",orderNum = "13")
    private String coordinateRadius;
    @Excel(name = "店铺介绍",orderNum = "4")
    private String introduce;


    //以下字段数据表里没有
    //距离
    private double distance;
    //是否点赞
    private String isFabulous;
    @Excel(name = "归属景区名称",orderNum = "3")
    private String scenicSpotName;

    private List<SysFeaturedFood> sysFeaturedFoodList;
}
