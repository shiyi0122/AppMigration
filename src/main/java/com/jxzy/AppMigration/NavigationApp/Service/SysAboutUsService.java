package com.jxzy.AppMigration.NavigationApp.Service;

import com.jxzy.AppMigration.NavigationApp.entity.SysAboutUs;
import com.jxzy.AppMigration.NavigationApp.entity.SysUserFans;
import com.jxzy.AppMigration.NavigationApp.util.PageDataResult;

/**
 * @Author zhang
 * @Date 2023/1/13 15:04
 */
public interface SysAboutUsService {
    int addSysAboutUs(SysAboutUs sysAboutUs);

    int editSysAboutUs(SysAboutUs sysAboutUs);

    PageDataResult getSysAboutUsList(String type,String subversionId);

    int delSysAboutUs(Long id);

    SysAboutUs getSysUserFans(String type);

    SysAboutUs getSysUserFansNew(String type, String subversionId);

}
