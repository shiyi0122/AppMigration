package com.jxzy.AppMigration.NavigationApp.entity;

import lombok.Data;

import java.util.List;

/**
 * @Author zhang
 * @Date 2023/1/10 17:56
 */
@Data
public class SysGoodThings {

    private Long id;

    private Long goodThingsId;

    private String goodThingsName;

    private String price;

    private Long recommendNumber;

    private String coverPic;

    private String createTime;

    private String updateTime;

    private String isFabulous;


}
