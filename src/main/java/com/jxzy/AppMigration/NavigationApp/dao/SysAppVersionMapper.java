package com.jxzy.AppMigration.NavigationApp.dao;

import com.jxzy.AppMigration.NavigationApp.entity.SysAppVersion;

import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2023/3/20 17:38
 */
public interface SysAppVersionMapper {
    SysAppVersion getAppNumberNew(String packageType);

    int insertSelective(SysAppVersion sysAppVersion);

    int updateSelective(SysAppVersion sysAppVersion);

    int deleteByPrimaryKey(Long id);

    List<SysAppVersion> getAdminSysAppVersionList(Map<String, Object> search);

    SysAppVersion getSysAppVersionUpToDate();

    SysAppVersion selectById(Long id);

    SysAppVersion getSysAppVersionUpToDateNew(String spotType);

}
