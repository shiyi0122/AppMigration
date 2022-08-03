package com.jxzy.AppMigration.NavigationApp.entity;

import com.jxzy.AppMigration.NavigationApp.entity.base.BaseDTO;
import lombok.Data;

/**
 * @Author zhang
 * @Date 2022/7/30 13:33
 */
@Data
public class SysScenicSpotBanner extends BaseDTO {

    private Long id;

    private String content;

    private String url;

    private String spotId;

    private Integer sort;

    private Integer type;

    private String createTime;

    private String updateTime;




}
