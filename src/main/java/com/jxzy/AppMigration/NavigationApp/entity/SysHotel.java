package com.jxzy.AppMigration.NavigationApp.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

import java.util.List;

/**
 * @Author zhang
 * @Date 2023/1/11 15:02
 * 酒店民宿
 */
@Data
public class SysHotel {

    private Long id;
    @Excel(name = "酒店民宿名称",orderNum = "1")
    private String hotelName;
    @Excel(name = "酒店民宿名称拼音",orderNum = "2")
    private String hotelPinyin;

    private String spotId;
    @Excel(name = "类型" ,replace = {"酒店_1" , "民宿_2"},orderNum = "3")
    private String type;
    @Excel(name = "百度坐标" ,orderNum = "4")
    private String hotelGpsBaiDu;
    @Excel(name = "高德坐标" ,orderNum = "5")
    private String hotelGpsGaoDe;
    @Excel(name = "84坐标",orderNum = "6")
    private String hotelGps;
    @Excel(name = "价格" ,orderNum = "7")
    private String price;
    @Excel(name = "地址" ,orderNum = "8")
    private String address;

    private String ascriptionId;
    @Excel(name = "创建时间",orderNum = "9",importFormat = "yyyy-MM-dd hh:mm:ss")
    private String createTime;

    private String updateTime;

    private Long province;

    private Long city;

    private Long area;

    private String coverPic;

    private String detailsPic;
    @Excel(name = "手机号",orderNum = "10")
    private String phone;
    @Excel(name = "营业时间",orderNum = "11")
    private String businessHours;

    private String recommendNumber;
    @Excel(name = "播报内容",orderNum = "12")
    private String broadcastContent;
    @Excel(name = "坐标半径",orderNum = "13")
    private String coordinateRadius;

    private List<SysHotelDetails> sysHotelDetailsList;

    //下面字段数据表里没有
    private double distance;

    private String provinceName;

    private String cityName;

    private String areaName;
    @Excel(name = "归属景区名称",orderNum = "3")
    private String scenicSpotName;

}
