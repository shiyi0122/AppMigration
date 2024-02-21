package com.jxzy.AppMigration.NavigationApp.Service;

import com.jxzy.AppMigration.NavigationApp.entity.SysSpotDynamic;
import com.jxzy.AppMigration.NavigationApp.entity.SysSpotDynamicBanner;
import com.jxzy.AppMigration.NavigationApp.entity.SysSpotDynamicContent;
import com.jxzy.AppMigration.NavigationApp.util.PageDataResult;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2023/1/12 19:13
 */
public interface SysSpotDynamicService {

    PageDataResult getSysSpotDynamicAppList(Integer pageNum, Integer pageSize, Map<String, Object> search);


    int addSysSpotDynamic(MultipartFile file, SysSpotDynamic sysSpotDynamic);


    int editSysSpotDynamic(MultipartFile file, SysSpotDynamic sysSpotDynamic);


    int delSysSpotDynamic(Long id);

    PageDataResult getSysSpotDynamicList(Integer pageNum, Integer pageSize, Map<String, Object> search);


    PageDataResult getAllSysSpotDynamic(Map<String, Object> search);

    int addSysSpotDynamicN(SysSpotDynamic sysSpotDynamic);


    int editSysSpotDynamicN(SysSpotDynamic sysSpotDynamic);

    int addSysSpotDynamicBanner(SysSpotDynamicBanner sysSpotDynamicBanner);

    int editSysSpotDynamicBanner(SysSpotDynamicBanner sysSpotDynamicBanner);

    int delSysSpotDynamicBanner(Long id);

    PageDataResult getSysSpotDynamicBannerList(Long id, Integer pageNum, Integer pageSize);

    int addSysSpotDynamicContent(SysSpotDynamicContent sysSpotDynamicContent);


    int delSysSpotDynamicContent(Long  id);

    PageDataResult getSysSpotDynamicContentList(Long id, Integer pageNum, Integer pageSize);

    List<SysSpotDynamic> getAllSysSpotDynamicN(Map<String, Object> search);

    int addSysSpotDynamicBrowse(Long id);


    int editSysSpotDynamicContent(SysSpotDynamicContent sysSpotDynamicContent);


    int delSysSpotDynamicContentPic(Long id);

}
