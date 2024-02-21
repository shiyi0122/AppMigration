package com.jxzy.AppMigration.NavigationApp.dao;

import com.jxzy.AppMigration.NavigationApp.entity.SysSpotLandmark;

import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2023/1/16 17:04
 */
public interface SysSpotLandmarkMapper {
    int insert(SysSpotLandmark sysSpotLandmark);

    int update(SysSpotLandmark sysSpotLandmark);

    int delete(Long id);

    List<SysSpotLandmark> list(Map<String, Object> search);

}
