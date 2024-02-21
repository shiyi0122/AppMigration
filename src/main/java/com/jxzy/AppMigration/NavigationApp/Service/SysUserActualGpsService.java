package com.jxzy.AppMigration.NavigationApp.Service;

import com.jxzy.AppMigration.NavigationApp.entity.*;

import java.util.List;

/**
 * @Author zhang
 * @Date 2023/3/11 10:53
 */
public interface SysUserActualGpsService {
    List<SysUserGps> getUserGpsList(Long id, Long type,String cityName);

    int editUserGps(SysUserActualGps sysUserActualGps);


    List<UserRoleText> listByPhone(String userPhone);

    LockText getLock();

    int editLock(String onOff);

    LockText getLockNew();
}
