package com.jxzy.AppMigration.NavigationApp.entity;

import lombok.Data;

@Data
public class SysScenicSpotBroadcastExtendWithBLOBs extends SysScenicSpotBroadcastExtend {
    private String broadcastContent;

    private String pictureUrl;

}