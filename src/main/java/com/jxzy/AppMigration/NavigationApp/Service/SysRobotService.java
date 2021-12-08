package com.jxzy.AppMigration.NavigationApp.Service;


import com.jxzy.AppMigration.NavigationApp.entity.SysRobot;

import java.util.List;
import java.util.Map;

public interface SysRobotService {

    /**
     * 根据机器人ID查询机器人或者查询所有机器人
     * @param: search
     * @description: TODO
     * @author: qushaobei
     * @date: 2021/12/3 0003
     */
    List<SysRobot> queryRobotList(Map<String, Object> search);
}
