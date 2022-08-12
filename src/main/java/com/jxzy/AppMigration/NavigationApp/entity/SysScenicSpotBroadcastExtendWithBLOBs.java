package com.jxzy.AppMigration.NavigationApp.entity;

import lombok.Data;

import java.util.List;

@Data
public class SysScenicSpotBroadcastExtendWithBLOBs extends SysScenicSpotBroadcastExtend {
    private String broadcastContent;

    private String pictureUrl;

}