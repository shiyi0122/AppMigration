package com.jxzy.AppMigration.NavigationApp.entity;

import com.jxzy.AppMigration.NavigationApp.entity.base.BaseDTO;
import lombok.Data;

/**
 * @Author zhang
 * @Date 2022/8/16 16:49
 */
@Data
public class SysScenicSpotUserFootprint extends BaseDTO {

    private Long id ;

    private Long spotId;

    private String spotCoordinate;

    private String startTime;

    private String endTime;

    private String userId;




}
