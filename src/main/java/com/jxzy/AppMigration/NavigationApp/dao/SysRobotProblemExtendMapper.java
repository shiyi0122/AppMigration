package com.jxzy.AppMigration.NavigationApp.dao;

import com.jxzy.AppMigration.NavigationApp.entity.SysRobotProblemExtend;

import java.util.List;

/**
 * @Author zhang
 * @Date 2022/11/3 9:05
 */
public interface SysRobotProblemExtendMapper {


    int insertSelective(SysRobotProblemExtend sysRobotProblemExtend);

    int updateByPrimaryKeySelective(SysRobotProblemExtend sysRobotProblemExtend);


    int deleteByPrimaryKey(Long extendId);

    List<SysRobotProblemExtend> getSysRobotProblemExtendList(Long id);

}
