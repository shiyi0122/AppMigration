package com.jxzy.AppMigration.NavigationApp.Service;

import com.jxzy.AppMigration.NavigationApp.entity.SysSpotLandmark;
import com.jxzy.AppMigration.NavigationApp.util.PageDataResult;

import java.util.Map;

/**
 * @Author zhang
 * @Date 2023/1/16 17:03
 */
public interface SysSpotLandmarkService {

    int addSpotLandmark(SysSpotLandmark sysSpotLandmark);


    int editSpotLandmark(SysSpotLandmark sysSpotLandmark);

    int delSpotLandmark(Long id);

    PageDataResult getSpotLandmarkList(Integer pageNum, Integer pageSize, Map<String, Object> search);

    PageDataResult getAdminSpotLandmarkList(Integer pageNum, Integer pageSize, Map<String, Object> search);

}
