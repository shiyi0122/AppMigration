package com.jxzy.AppMigration.NavigationApp.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

import java.util.List;

/**
 * @Author zhang
 * @Date 2023/1/10 17:47
 */
@Data
public class SysGoodThingsShop {

    private Long id;

    private Long spotId;
    @Excel(name = "地道好物名称",orderNum = "1")
    private String goodThingsName;
    @Excel(name = "地道好物名称拼音",orderNum = "2")
    private String goodThingsPinyin;
    @Excel(name = "百度坐标",orderNum = "3")
    private String goodThingsGpsBaiDu;
    @Excel(name = "高德坐标",orderNum = "4")
    private String goodThingsGpsGaoDe;
    @Excel(name = "84坐标",orderNum = "5")
    private String goodThingsGps;
    @Excel(name = "好物店铺介绍",orderNum = "6")
    private String goodThingsIntroduce;
    @Excel(name = "手机号",orderNum = "7")
    private String phone;
    @Excel(name = "营业时间",orderNum = "8")
    private String businessHours;

    private Long recommendNumber;

    private String coverPic;

    private String detailsPic;
    @Excel(name = "创建时间",orderNum = "9",importFormat = "yyyy-MM-dd hh:mm:ss")
    private String createTime;

    private String updateTime;

    private Long province;

    private Long city;

    private Long area;
    @Excel(name = "地址",orderNum = "10")
    private String address;
    @Excel(name = "坐标半径",orderNum = "11")
    private String coordinateRadius;
    @Excel(name = "播报内容",orderNum = "12")
    private String  broadcastContent;
    @Excel(name = "人均消费",orderNum = "13")
    private String price;
    @Excel(name = "介绍",orderNum = "14")
    private String introduce;

    private String provinceName;

    private String cityName;

    private String areaName;


    //下面字段不在数据表中
    private double distance;

    private String isFabulous;
    @Excel(name = "归属景区",orderNum = "2")
    private String scenicSpotName;

    private List<SysGoodThings> list;
}
