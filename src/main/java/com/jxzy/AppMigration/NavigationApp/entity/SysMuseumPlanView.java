package com.jxzy.AppMigration.NavigationApp.entity;

import lombok.Data;

/**
 * @Author zhang
 * @Date 2023/8/7 18:11
 */
@Data
public class SysMuseumPlanView {

    private Long id;

    private Long museumId;

    private String floor;

    private String planView;

    private String createTime;

    private String updateTime;
}
