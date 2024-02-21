package com.jxzy.AppMigration.NavigationApp.Service;

import com.jxzy.AppMigration.NavigationApp.entity.SysAppVersion;
import com.jxzy.AppMigration.NavigationApp.util.PageDataResult;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * @Author zhang
 * @Date 2023/3/20 17:35
 */
public interface SysAppVersionService {


    int addAdminSysAppVersion(SysAppVersion sysAppVersion);


    SysAppVersion getAppNumberNew(String packageType);

    int editAdminSysAppVersion(SysAppVersion sysAppVersion);

    int delAdminSysAppVersion(Long id);

    PageDataResult getAdminSysAppVersionList(Integer pageNum, Integer pageSize, Map<String, Object> search);

    SysAppVersion getSysAppVersionUpToDate();


    int editAdminSysAppVersionEnableDisable(Long id, String appType);

    SysAppVersion getSysAppVersionUpToDateNew(String spotType);


    int addAdminSysAppVersionNew(MultipartFile file, SysAppVersion sysAppVersion);
}
