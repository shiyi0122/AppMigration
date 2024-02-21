package com.jxzy.AppMigration.NavigationApp.dao;

import com.jxzy.AppMigration.NavigationApp.entity.SysSpotDynamicContent;

import java.util.List;

/**
 * @Author zhang
 * @Date 2023/1/17 19:48
 */
public interface SysSpotDynamicContentMapper {
    int insert(SysSpotDynamicContent sysSpotDynamicContent);


    int delete(Long id);

    List<SysSpotDynamicContent> list(Long id);


    int update(SysSpotDynamicContent sysSpotDynamicContent);

    int delSysSpotDynamicContentPic(Long id);
}
