package com.jxzy.AppMigration.NavigationApp.Service;

import com.jxzy.AppMigration.NavigationApp.entity.SysConfigs;

public interface SysConfigsService {
    /**
     * 关于我们
     * @param: configsId
     * @description: TODO
     * @return: com.jxzy.AppMigration.NavigationApp.entity.SysConfigs
     * @author: qushaobei
     * @date: 2022/8/17 0017
     */
    SysConfigs queryAboutUs(long configsId);
}
