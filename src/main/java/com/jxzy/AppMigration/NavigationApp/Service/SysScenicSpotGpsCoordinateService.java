package com.jxzy.AppMigration.NavigationApp.Service;

import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotGpsCoordinateWithBLOBs;

import java.util.List;
import java.util.Map;

public interface SysScenicSpotGpsCoordinateService {
    /**
     * 查询景区电子围栏
     * @param: search
     * @description: TODO
     * @author: qushaobei
     * @date: 2021/11/5 0005
     */
    SysScenicSpotGpsCoordinateWithBLOBs queryScenicSpotElectronicFence(Map<String, Object> search);

    /**
     * 查询所有景区电子围栏
     * @param:
     * @description: TODO
     * @return: java.util.List<com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotGpsCoordinateWithBLOBs>
     * @author: qushaobei
     * @date: 2022/8/3 0003
     */
    List<SysScenicSpotGpsCoordinateWithBLOBs> queryLocationScenicSpot();
}
