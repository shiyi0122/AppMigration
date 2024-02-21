package com.jxzy.AppMigration.NavigationApp.entity;

import lombok.Data;

/**
 * @ProjectName: rc
 * @Package: com.hna.hka.archive.management.system.model
 * @ClassName: SysRobotGPS
 * @Author: 郭凯
 * @Description: 机器人位置坐标返回对象
 * @Date: 2021/3/24 14:17
 * @Version: 1.0
 */
@Data
public class SysRobotGPS {

    private String robotCode;

    private String robotGpsBaiDu;

    private String robotGpsGpgga;

    private String robotGpsSmallApp;

    private String robotPowerState;

    private String robotRunState;

    private Long scenicSpotId;

    private String scenicSpotName;

    private String createDate;

    private String pushStatus;

    private String robotAdminLocking;

    private String robotCodeCid;

    private String robotCodeSim;

    private String robotFaultState;

    private String robotId;

    private String robotPollingType;

    private String robotType;

    private String clientVersion; //pad端软件版本号

    private String robotRemarks;
}
