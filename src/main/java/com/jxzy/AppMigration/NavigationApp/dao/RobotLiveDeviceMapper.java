package com.jxzy.AppMigration.NavigationApp.dao;

import com.jxzy.AppMigration.NavigationApp.entity.SysRobotLiveDevice;
import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotBinding;

import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2023/5/25 11:28
 */
public interface RobotLiveDeviceMapper {

   List<SysRobotLiveDevice> getRecommendRobotDevice(Map<String,Object> search);


    List<SysRobotLiveDevice> getRobotDevice(Map<String, String> search);

    List<SysScenicSpotBinding> getSpotDevice(Map<String, String> search);

    List<SysScenicSpotBinding> getCityDevice();


    int updateByDeviceIdSelective(SysRobotLiveDevice sysRobotLiveDevice);


}
