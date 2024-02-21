package com.jxzy.AppMigration.NavigationApp.dao;

import com.jxzy.AppMigration.NavigationApp.entity.SysNavigation;

import java.util.List;

/**
 * @Author zhang
 * @Date 2023/1/9 15:43
 */
public interface SysNavigationMapper {
    int insertSelective(SysNavigation sysNavigation);

    int updateSelective(SysNavigation sysNavigation);

    int deleteByPrimaryKey(Long id);

    List<SysNavigation> list(String content);

}
