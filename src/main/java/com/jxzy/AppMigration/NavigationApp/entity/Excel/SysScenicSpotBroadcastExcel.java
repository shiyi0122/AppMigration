package com.jxzy.AppMigration.NavigationApp.entity.Excel;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

/**
 * @Author zhang
 * @Date 2022/9/15 14:51
 */
@Data
public class SysScenicSpotBroadcastExcel {
//    @Excel(name = "景点id",orderNum = "14")
    private Long  broadcastId;

    @Excel(name = "景区名称",orderNum = "1")
    private String scenicSpotName;

    @Excel(name = "景点名称",orderNum = "2")
    private String broadcastName;

    @Excel(name = "景点名称拼音格式",orderNum = "3")
    private String pinYinName;

    @Excel(name = "WGS84坐标",orderNum = "4")
    private String broadcastGps;

    @Excel(name = "百度坐标",orderNum = "5")
    private String broadcastGpsBaiDu;


    @Excel(name = "坐标半径",orderNum = "7")
    private String scenicSpotRange;

    @Excel(name = "特色景点状态",orderNum = "8")
    private String  isFeature;

    @Excel(name = "创建时间",orderNum = "9",importFormat = "yyyy-MM-dd hh:mm:ss")
    private String createDate;

    @Excel(name = "特色内容描述",orderNum = "10")
    private String  featureIntroduce;

    @Excel(name = "景点图片" ,orderNum = "11")
    private String  pictureUrl;

    @Excel(name = "游览时长",orderNum = "12")
    private String tourDuration;

    @Excel(name = "景点介绍",orderNum = "13")
    private String introduce;




}
