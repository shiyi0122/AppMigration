package com.jxzy.AppMigration.NavigationApp.dao;

import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotGpsCoordinate;
import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotGpsCoordinateWithBLOBs;

import java.util.Map;

public interface SysScenicSpotGpsCoordinateMapper {
    int deleteByPrimaryKey(Long coordinateId);

    int insert(SysScenicSpotGpsCoordinateWithBLOBs record);

    int insertSelective(SysScenicSpotGpsCoordinateWithBLOBs record);

    SysScenicSpotGpsCoordinateWithBLOBs selectByPrimaryKey(Long coordinateId);

    int updateByPrimaryKeySelective(SysScenicSpotGpsCoordinateWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(SysScenicSpotGpsCoordinateWithBLOBs record);

    int updateByPrimaryKey(SysScenicSpotGpsCoordinate record);

    /**
     * 查询景区电子围栏
     * @param: search
     * @description: TODO
     * @author: qushaobei
     * @date: 2021/11/5 0005
     */
    SysScenicSpotGpsCoordinateWithBLOBs queryScenicSpotElectronicFence(Map<String, Object> search);
}