package com.jxzy.AppMigration.NavigationApp.Service;

import com.jxzy.AppMigration.NavigationApp.entity.SysSpotNavigation;
import com.jxzy.AppMigration.NavigationApp.util.PageDataResult;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author zhang
 * @Date 2023/1/13 9:45
 */
public interface SysSpotNavigationService {
    int addSysSpotNavigation(MultipartFile file, SysSpotNavigation sysSpotNavigation);

    int editSysSpotNavigation(MultipartFile file, SysSpotNavigation sysSpotNavigation);

    int delSysSpotNavigation(Long id);

    PageDataResult getSysSpotNavigationList(Integer pageNum, Integer pageSize, String content);


}
