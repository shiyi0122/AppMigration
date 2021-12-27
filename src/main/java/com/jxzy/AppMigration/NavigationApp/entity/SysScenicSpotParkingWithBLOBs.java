package com.jxzy.AppMigration.NavigationApp.entity;

import lombok.Data;

@Data
public class SysScenicSpotParkingWithBLOBs extends SysScenicSpotParking {
    private String parkingCoordinateGroup;

    private String parkingCoordinateGroupBaidu;

}