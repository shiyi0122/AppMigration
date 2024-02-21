package com.jxzy.AppMigration.NavigationApp.entity;

import lombok.Data;

/**
 * @Author zhang
 * @Date 2023/5/23 15:51
 */
@Data
public class BusinessUserLookTime {

    private Long id;

    private Long userId;

    private String robotCode;

    private String viewingTime;

    private String createTime;

    private String updateTime;

    private String dataSide;


}
