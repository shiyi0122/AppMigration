package com.jxzy.AppMigration.NavigationApp.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

import java.util.List;

/**
 * @Author zhang
 * @Date 2023/1/11 17:03
 * 娱乐项目
 */
@Data
public class SysEntertainment {

    private Long id ;
    @Excel(name = "娱乐名称",orderNum = "1")
    private String  entertainmentName;
    @Excel(name = "娱乐名称拼音",orderNum = "2")
    private String entertainmentPinyin;
    @Excel(name = "百度坐标",orderNum = "4")
    private String entertainmentGpsBaiDu;
    @Excel(name = "高德坐标",orderNum = "5")
    private String entertainmentGpsGaoDe;
    @Excel(name = "坐标半径",orderNum = "6")
    private String coordinateRadius;
    @Excel(name = "84坐标",orderNum = "7")
    private String entertainmentGps;
    @Excel(name = "价格",orderNum = "8")
    private String price;

    private Long ascriptionSpotId;

    private String coverPic;
    @Excel(name = "创建时间",orderNum = "9")
    private String createTime;

    private String updateTime;

    private String detailsPic;

    private String province;

    private String city;

    private String area;
    @Excel(name = "营业时间",orderNum = "10")
    private String businessHours;

    private String recommendNumber;
    @Excel(name = "地址",orderNum = "11")
    private String address;
    @Excel(name = "播报内容",orderNum = "12")
    private String broadcastContent;

    private String isPeriphery;

    private String provinceName;

    private String cityName;

    private String areaName;

    private List<SysEntertainmentDetails> SysEntertainmentDetailsList;

    //以下字段数据表中没有
    private double distance;
    @Excel(name = "景区名称",orderNum = "3")
    private String scenicSpotName;


}
