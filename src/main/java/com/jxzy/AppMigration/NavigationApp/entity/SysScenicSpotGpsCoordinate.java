package com.jxzy.AppMigration.NavigationApp.entity;

import lombok.Data;

@Data
public class SysScenicSpotGpsCoordinate {
    private Long coordinateId;

    private Long coordinateScenicSpotId;

    private String insideWarning;

    private String coordinateParkingType;

    private String createDate;

    private String updateDate;
}