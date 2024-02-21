package com.jxzy.AppMigration.NavigationApp.entity.Excel;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

/**
 * @Author zhang
 * @Date 2022/11/3 13:23
 */
@Data
public class SysScenicSpotParkingExcel {

    @Excel(name = "出入口名称",orderNum = "1")
    private String parkingName;
    @Excel(name = "出入口内容",orderNum = "2")
    private String parkingContent;
    @Excel(name = "景区状态" ,replace = {"启用_1" , "未启用_0"},orderNum = "3")
    private String parkingType;

    @Excel(name = "创建时间",orderNum = "4")
    private String createDate;
    @Excel(name = "修改时间",orderNum = "5")
    private String updateDate;
    @Excel(name = "景区名称",orderNum = "6")
    private String spotName;
    @Excel(name = "出入口拼音",orderNum = "7")
    private String  parkingPinyinName;
    @Excel(name = "出入口坐标半径",orderNum = "8")
    private String parkingRange;
    @Excel(name = "84坐标",orderNum = "9")
    private String parkingCoordinateGroup;
    @Excel(name = "百度坐标",orderNum = "10")
    private String parkingCoordinateGroupBaidu;
    @Excel(name = "高德坐标",orderNum = "10")
    private String parkingCoordinateGroupGaode;


}
