package com.jxzy.AppMigration.NavigationApp.Service;

import com.jxzy.AppMigration.NavigationApp.entity.SysGuideAppUsers;
import com.jxzy.AppMigration.NavigationApp.entity.SysRobotMapRes;
import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotBinding;

import java.util.List;
import java.util.Map;

public interface SysRobotMapResService {
    /**
     * 查询地图资源
     * @param: search
     * @description: TODO
     * @author: qushaobei
     * @date: 2021/11/11 0011
     */
    List<SysRobotMapRes> queryMapRes(Map<String, Object> search);
}
