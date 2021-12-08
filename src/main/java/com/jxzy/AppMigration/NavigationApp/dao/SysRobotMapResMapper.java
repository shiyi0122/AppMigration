package com.jxzy.AppMigration.NavigationApp.dao;

import com.jxzy.AppMigration.NavigationApp.entity.SysRobotMapRes;
import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotBinding;

import java.util.List;
import java.util.Map;

public interface SysRobotMapResMapper {
    int deleteByPrimaryKey(Long resId);

    int insert(SysRobotMapRes record);

    int insertSelective(SysRobotMapRes record);

    SysRobotMapRes selectByPrimaryKey(Long resId);

    int updateByPrimaryKeySelective(SysRobotMapRes record);

    int updateByPrimaryKey(SysRobotMapRes record);

    /**
     * 查询地图资源
     * @param: search
     * @description: TODO
     * @author: qushaobei
     * @date: 2021/11/11 0011
     */
    List<SysRobotMapRes> queryMapRes(Map<String, Object> search);
}