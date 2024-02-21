package com.jxzy.AppMigration.NavigationApp.entity.Excel;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import io.swagger.models.auth.In;
import lombok.Data;

/**
 * @Author zhang
 * @Date 2022/9/15 14:51
 */
@Data
public class SysScenicSpotFilesExcel {
//    @Excel(name = "景区id",orderNum = "14")
    private Long scenicSpotId;

    @Excel(name = "景区名称",orderNum = "1")
    private String scenicSpotName;

    @Excel(name = "景区拼音名称",orderNum = "2")
    private String pinYingName;

    @Excel(name = "景区图片",orderNum = "3")
    private String scenicSpotImgUrl;

    @Excel(name = "景区宣传语",orderNum = "4")
    private String scenicSpotSlogan;

    @Excel(name = "景区介绍",orderNum = "5")
    private String introduction;
//
//    @Excel(name = "景区音频地址",orderNum = "6")
//    private String scneicSpotAudioUrl;

    @Excel(name = "百度坐标",orderNum = "7")
    private String coordinateRangeBaiDu;

    @Excel(name = "84坐标",orderNum = "8")
    private String coordinateRange;

    @Excel(name = "创建时间",orderNum = "9",importFormat = "yyyy-MM-dd hh:mm:ss")
    private String createDate;

    @Excel(name = "坐标半径",orderNum = "10")
    private String coordinateRangeAdius;

    @Excel(name = "运营状态" ,replace = {"已运营_1" , "测试_2" , "预运营_3"},orderNum = "11")
    private String robotWakeupWords;


    @Excel(name = "景区联系人" ,orderNum = "12")
    private String scenicSpotContact;

    @Excel(name = "景区联系人手机号" ,orderNum = "13")
    private String  scenicSpotPhone;

}
