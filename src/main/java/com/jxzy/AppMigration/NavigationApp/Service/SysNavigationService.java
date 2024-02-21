package com.jxzy.AppMigration.NavigationApp.Service;

import com.jxzy.AppMigration.NavigationApp.entity.SysNavigation;
import com.jxzy.AppMigration.NavigationApp.util.PageDataResult;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author zhang
 * @Date 2023/1/9 15:41
 */

public interface SysNavigationService {

    int addSysNavigation(MultipartFile file, SysNavigation sysNavigation);

    int editSysNavigation(MultipartFile file, SysNavigation sysNavigation);

    int delSysNavigation(Long id);

    PageDataResult getSysNavigationList(Integer pageNum, Integer pageSize, String content);
}
