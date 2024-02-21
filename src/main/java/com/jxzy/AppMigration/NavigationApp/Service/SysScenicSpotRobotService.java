package com.jxzy.AppMigration.NavigationApp.Service;

import com.jxzy.AppMigration.NavigationApp.entity.SysRobot;
import com.jxzy.AppMigration.NavigationApp.entity.SysRobotGPS;
import com.jxzy.AppMigration.NavigationApp.entity.SysUserGps;

import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2022/9/23 10:40
 */
public interface SysScenicSpotRobotService {
    List<SysRobotGPS> sysScenicSpotRobotList(Map<String, Object> search);

    List<SysUserGps> sysScenicSpotUserAndRobotList(Long scenicSpotFid);

}
