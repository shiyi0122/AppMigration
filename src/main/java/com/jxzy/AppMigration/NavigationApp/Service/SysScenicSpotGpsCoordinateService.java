package com.jxzy.AppMigration.NavigationApp.Service;

import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotGpsCoordinate;
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

    /**\
     * 根据景区id查询围栏
     * @param coordinateScenicSpotId
     * @return
     */
    SysScenicSpotGpsCoordinateWithBLOBs getSpotIdByGpsCoordinate(Long coordinateScenicSpotId);

    /**
     * 定时任务中添加围栏
     * @param sysScenicSpotGpsCoordinateWithBLOBs
     * @return
     */
    int insert(SysScenicSpotGpsCoordinateWithBLOBs sysScenicSpotGpsCoordinateWithBLOBs);

    /**
     * 根据id查询
     * @param coordinateId
     * @return
     */
    SysScenicSpotGpsCoordinateWithBLOBs getSpotGpsCoordinateId(Long coordinateId);

    /**
     * 修改围栏信息
     * @param sysScenicSpotGpsCoordinateWithBLOBs
     * @return
     */
    int edit(SysScenicSpotGpsCoordinateWithBLOBs sysScenicSpotGpsCoordinateWithBLOBs);


}
