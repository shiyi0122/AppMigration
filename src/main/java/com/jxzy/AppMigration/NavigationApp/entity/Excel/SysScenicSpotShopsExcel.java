package com.jxzy.AppMigration.NavigationApp.entity.Excel;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

/**
 * @Author zhang
 * @Date 2022/9/19 19:10
 */
@Data
public class SysScenicSpotShopsExcel {
    @Excel(name = "店铺id",orderNum = "10")
    private Long shopsId;
    @Excel(name = "景区名称",orderNum = "1")
    private String spotName;
    @Excel(name = "店铺名称",orderNum = "2")
    private String shopsName;
    @Excel(name = "店铺手机号",orderNum = "3")
    private String shopsPhone;
    @Excel(name = "店铺拼音名称",orderNum = "4")
    private String shopsPinyinName;
    @Excel(name = "店铺84坐标",orderNum = "5")
    private String shopsGps;
    @Excel(name = "店铺百度坐标",orderNum = "6")
    private String shopsGpsBaidu;
    @Excel(name = "店铺高德坐标",orderNum = "6")
    private String shopsGpsGaode;
    @Excel(name = "店铺介绍",orderNum = "7")
    private String introduction;
    @Excel(name = "商品介绍",orderNum = "7")
    private String productIntroduction;

    @Excel(name = "坐标半径",orderNum = "8")
    private String shopsRange;

    @Excel(name = "店铺地址",orderNum = "9")
    private String shopsAddress;

    private Long scenicSpotId;

    private String createTime;

    private String updateTime;
}
