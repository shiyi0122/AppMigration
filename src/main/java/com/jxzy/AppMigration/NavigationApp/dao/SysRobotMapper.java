package com.jxzy.AppMigration.NavigationApp.dao;

import com.jxzy.AppMigration.NavigationApp.entity.SysRobot;
import com.jxzy.AppMigration.NavigationApp.entity.SysRobotGPS;

import java.util.List;
import java.util.Map;

public interface SysRobotMapper {
    int deleteByPrimaryKey(Long robotId);

    int insert(SysRobot record);

    int insertSelective(SysRobot record);

    SysRobot selectByPrimaryKey(Long robotId);

    int updateByPrimaryKeySelective(SysRobot record);

    int updateByPrimaryKey(SysRobot record);

    /**
     * 根据机器人ID查询机器人或者查询所有机器人
     * @param: search
     * @description: TODO
     * @author: qushaobei
     * @date: 2021/12/3 0003
     */
    List<SysRobot> queryRobotList(Map<String, Object> search);

    List<SysRobot> getRobotGpsList(Map<String, Object> search);


    SysRobotGPS getRobotGPSByRobotCode(String robotCode);

    List<SysRobot> getRobotGpsSpotList(Long scenicSpotFid);

}