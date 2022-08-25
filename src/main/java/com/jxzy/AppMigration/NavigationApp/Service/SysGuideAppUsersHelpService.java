package com.jxzy.AppMigration.NavigationApp.Service;

import com.jxzy.AppMigration.NavigationApp.entity.SysGuideAppUsers;
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

    /**
     * 查询使用帮助
     * @param: helpId
     * @description: TODO
     * @return: com.jxzy.AppMigration.NavigationApp.entity.SysGuideAppUsersHelp
     * @author: qushaobei
     * @date: 2022/8/17 0017
     */
    SysGuideAppUsersHelp queryUserHelp(Long helpId);

    /**
     * 使用帮助搜索
     * @param: helpName
     * @description: TODO
     * @return: java.util.List<com.jxzy.AppMigration.NavigationApp.entity.SysGuideAppUsersHelp>
     * @author: qushaobei
     * @date: 2022/8/19 0019
     */
    List<SysGuideAppUsersHelp> queryUserHelpData(String helpName);


}
