package com.jxzy.AppMigration.NavigationApp.entity;

import com.jxzy.AppMigration.NavigationApp.entity.base.BaseDTO;
import lombok.Data;

/**
 * @Author zhang
 * @Date 2022/8/17 10:44
 */
@Data
public class SysScenicSpotUserStop extends BaseDTO {


    private Long id ;

    private Long spotId;

    private Long broadcastId;

    private String  startTime;

    private String endTime;

    private String time;

    private String createTime;

    private String updateTime;

    private Long userId;

    private String type;

    private String  scenicSpotName;

    private String    broadcastName;

}
