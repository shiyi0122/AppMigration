package com.jxzy.AppMigration.NavigationApp.Service;

import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotGpsCoordinateWithBLOBs;

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
}
