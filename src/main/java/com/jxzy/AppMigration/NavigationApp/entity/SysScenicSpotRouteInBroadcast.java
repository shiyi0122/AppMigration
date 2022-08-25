package com.jxzy.AppMigration.NavigationApp.entity;

import com.jxzy.AppMigration.NavigationApp.entity.base.BaseDTO;
import lombok.Data;

/**
 * @Author zhang
 * @Date 2022/8/9 16:36
 */
@Data
public class SysScenicSpotRouteInBroadcast extends BaseDTO {

    private Long id;

    private Long sysScenicSpotRouteId;

    private Long sysScenicSpotBroadcastId;

    private String sort;

    private String createTime;

    private String updateTime;

    private String broadcastName;

    private String broadcastGpsBaiDu;

    private String broadcastGps;

    private String  broadcastContent;

    private String mediaResourceUrl;

}
