package com.jxzy.AppMigration.NavigationApp.dao;

import com.jxzy.AppMigration.NavigationApp.entity.SysNavigation;
import com.jxzy.AppMigration.NavigationApp.entity.SysSpotNavigation;

import java.util.List;

/**
 * @Author zhang
 * @Date 2023/1/13 9:47
 */
public interface SysSpotNavigationMapper {
    int insertSelective(SysSpotNavigation sysSpotNavigation);


    int updateSelective(SysSpotNavigation sysSpotNavigation);


    int deleteByPrimaryKey(Long id);

    List<SysNavigation> list(String content);

}
