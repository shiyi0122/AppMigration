package com.jxzy.AppMigration.NavigationApp.Service;

import java.util.Map;

/**
 * @Author zhang
 * @Date 2023/5/18 13:27
 */
public interface BusinessUserLiveDeviceExperienceLogService {
    String getRobotResidueTime(String robotCode, String userId);


    Map<String, Object> getRobotResidueTimeN(String robotCode, String userId);
}
