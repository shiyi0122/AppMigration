package com.jxzy.AppMigration.NavigationApp.entity;

import lombok.Data;

@Data
public class SysScenicSpotGpsCoordinateWithBLOBs extends SysScenicSpotGpsCoordinate {
    private String coordinateOuterring;

    private String coordinateOuterringBaiDu;

    private String warningLoopCoordinateGroup;
}