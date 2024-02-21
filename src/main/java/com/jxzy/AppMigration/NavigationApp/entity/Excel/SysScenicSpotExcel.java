package com.jxzy.AppMigration.NavigationApp.entity.Excel;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

/**
 * @Author zhang
 * @Date 2022/9/15 14:51
 */
@Data
public class SysScenicSpotExcel {
//    @Excel(name = "景区id",orderNum = "14")
    private Long scenicSpotId;

    @Excel(name = "景区名称",orderNum = "1")
    private String scenicSpotName;

    @Excel(name = "景区星级",orderNum = "2")
    private Long startLevel;

    @Excel(name = "联系人",orderNum = "3")
    private String scenicSpotContact;

    @Excel(name = "联系电话",orderNum = "4")
    private String scenicSpotPhone;

    @Excel(name = "景区邮箱",orderNum = "5")
    private String scenicSpotEmail;


    @Excel(name = "景区地址",orderNum = "7")
    private String scenicSpotAddres;

    @Excel(name = "景区邮编",orderNum = "8")
    private String scenicSpotPostalCode;

    @Excel(name = "创建时间",orderNum = "9",importFormat = "yyyy-MM-dd hh:mm:ss")
    private String createDate;

    @Excel(name = "景区归属地",orderNum = "10")
    private String scenicSpotFname;

    @Excel(name = "景区状态" ,replace = {"已运营_1" , "测试_2" , "预运营_3"},orderNum = "11")
    private String robotWakeupWords;

    @Excel(name = "景区归属公司",orderNum = "12")
    private String scenicSpotCompany;




}
