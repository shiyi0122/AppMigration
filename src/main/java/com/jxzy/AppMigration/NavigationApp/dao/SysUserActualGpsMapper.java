package com.jxzy.AppMigration.NavigationApp.dao;

import com.jxzy.AppMigration.NavigationApp.entity.LockText;
import com.jxzy.AppMigration.NavigationApp.entity.SysUserActualGps;
import com.jxzy.AppMigration.NavigationApp.entity.SysUserGps;
import com.jxzy.AppMigration.NavigationApp.entity.UserRoleText;

import java.util.List;

/**
 * @Author zhang
 * @Date 2023/3/11 10:54
 */

public interface SysUserActualGpsMapper {
    List<SysUserActualGps> getUserGpsCityIdList(Long id);

    List<SysUserActualGps> getUserGpsSpotIdList(Long id);

    List<SysUserActualGps> getUserGpsBroadcastIdList(Long id);

    SysUserActualGps getUserIdByGps(Long userId);

    int insert(SysUserActualGps sysUserActualGps);


    int update(SysUserActualGps sysUserActualGps);

    List<UserRoleText> listByPhone(String userPhone);

    LockText getLock();

    int editLock(String onOff);

    LockText getLockNew();
}
