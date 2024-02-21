package com.jxzy.AppMigration.NavigationApp.entity.Excel;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

/**
 * @Author zhang
 * @Date 2022/9/19 11:23
 */
@Data
public class SysScenicReommendedRoute {

    @Excel(name = "景区名称",orderNum = "1")
    private String scenicSpotName ;

    @Excel(name = "途径景点名称",orderNum = "2")
    private String broadcastName;

    @Excel(name = "线路名称",orderNum = "3")
    private String  routeName;
    @Excel(name = "线路介绍",orderNum = "4")
    private String  routeIntroduce;
    @Excel(name = "线路84坐标组",orderNum = "5")
    private String  routeGps;
    @Excel(name = "线路百度坐标组",orderNum = "6")
    private String  routeGpsBaiDu;
    @Excel(name = "线路总长度",orderNum = "7")
    private String  totalKm;
    @Excel(name = "使用时间",orderNum = "8")
    private String  useTime;
    @Excel(name = "途径景点数",orderNum = "9")
    private String  broadcastCount;
    @Excel(name = "线路id",orderNum = "10")
    private Long routeId;
}
