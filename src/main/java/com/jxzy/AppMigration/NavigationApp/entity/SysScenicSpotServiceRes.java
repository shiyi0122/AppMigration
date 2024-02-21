package com.jxzy.AppMigration.NavigationApp.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

@Data
public class SysScenicSpotServiceRes {
    private Long serviceId;
    @Excel(name = "卫生间名称",orderNum = "2")
    private String serviceName;
    @Excel(name = "卫生间拼音",orderNum = "3")
    private String serviceNamePinYin;

    private String serviceType;

    private Long scenicSpotId;
    @Excel(name = "84坐标",orderNum = "4")
    private String serviceGps;
    @Excel(name = "百度坐标",orderNum = "5")
    private String serviceGpsBaiDu;
    @Excel(name = "介绍",orderNum = "7")
    private String serviceIntroduce;

    private String servicePic;
    @Excel(name = "创建时间",orderNum = "9",importFormat = "yyyy-MM-dd hh:mm:ss")
    private String createDate;

    private String updateDate;
    @Excel(name = "运营状态" ,replace = {"启用_1" , "禁用_0"},orderNum = "8")
    private String serviceState;
    @Excel(name = "坐标半径",orderNum = "10")
    private String serviceRadius;
    @Excel(name = "最大容纳数量",orderNum = "11")
    private String serviceMaxPeople;
    @Excel(name = "景区名称",orderNum = "1")
    private String scenicSpotName;
    @Excel(name = "高德坐标",orderNum = "6")
    private String  serviceGpsGaoDe;

}