package com.jxzy.AppMigration.NavigationApp.Service;

import com.jxzy.AppMigration.NavigationApp.entity.SysGuideAppUsersHelp;

import java.util.List;

public interface SysGuideAppUsersHelpService {
    /**
     * 获取使用帮助列表
     * @param:
     * @description: TODO
     * @author: qushaobei
     * @date: 2021/11/5 0005
     */
    List<SysGuideAppUsersHelp> queryUserHelpList();
}
