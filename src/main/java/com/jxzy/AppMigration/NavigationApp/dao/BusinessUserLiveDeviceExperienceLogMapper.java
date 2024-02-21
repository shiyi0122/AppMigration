package com.jxzy.AppMigration.NavigationApp.dao;


import com.jxzy.AppMigration.NavigationApp.entity.BusinessUserLiveDeviceExperienceLog;

import java.util.Map;

/**
 * @Author zhang
 * @Date 2023/5/18 13:28
 */
public interface BusinessUserLiveDeviceExperienceLogMapper {

    BusinessUserLiveDeviceExperienceLog getRobotResidueTime(Map<String, Object> search);


    int insertSelective(BusinessUserLiveDeviceExperienceLog businessUserLiveDeviceExperienceLogN);


    int updateSelective(BusinessUserLiveDeviceExperienceLog robotResidueTime);

}
