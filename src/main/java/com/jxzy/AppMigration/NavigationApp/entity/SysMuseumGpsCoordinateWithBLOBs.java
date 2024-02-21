package com.jxzy.AppMigration.NavigationApp.entity;

import lombok.Data;

@Data
public class SysMuseumGpsCoordinateWithBLOBs extends SysMuseumGpsCoordinate {
    private String coordinateOuterring;

    private String coordinateOuterringBaiDu;

    private String warningLoopCoordinateGroup;
}