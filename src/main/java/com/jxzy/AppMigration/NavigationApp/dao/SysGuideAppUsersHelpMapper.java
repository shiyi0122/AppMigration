package com.jxzy.AppMigration.NavigationApp.dao;

import com.jxzy.AppMigration.NavigationApp.entity.SysGuideAppUsersHelp;

import java.util.List;

public interface SysGuideAppUsersHelpMapper {
    int deleteByPrimaryKey(Long helpId);

    int insert(SysGuideAppUsersHelp record);

    int insertSelective(SysGuideAppUsersHelp record);

    SysGuideAppUsersHelp selectByPrimaryKey(Long helpId);

    int updateByPrimaryKeySelective(SysGuideAppUsersHelp record);

    int updateByPrimaryKeyWithBLOBs(SysGuideAppUsersHelp record);

    int updateByPrimaryKey(SysGuideAppUsersHelp record);

    /**
     * 获取使用帮助列表
     * @param:
     * @description: TODO
     * @author: qushaobei
     * @date: 2021/11/5 0005
     */
    List<SysGuideAppUsersHelp> queryUserHelpList();

    /**
     * 使用帮助搜索
     * @param: helpName
     * @description: TODO
     * @return: java.util.List<com.jxzy.AppMigration.NavigationApp.entity.SysGuideAppUsersHelp>
     * @author: qushaobei
     * @date: 2022/8/19 0019
     */
    List<SysGuideAppUsersHelp> queryUserHelpData(String helpTitle);
}