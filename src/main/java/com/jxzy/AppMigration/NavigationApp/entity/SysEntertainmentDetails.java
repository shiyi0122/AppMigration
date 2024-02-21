package com.jxzy.AppMigration.NavigationApp.entity;

import lombok.Data;

/**
 * @Author zhang
 * @Date 2023/4/24 10:43
 */
@Data
public class SysEntertainmentDetails {

    private Long id;

    private Long entertainmentId;

    private String entertainmentDetailsName;

    private String price;

    private Long recommendNumber;

    private String coverPic;

    private String createTime;

    private String updateTime;

}
