package com.jxzy.AppMigration.NavigationApp.dao;

import com.jxzy.AppMigration.NavigationApp.entity.SysAboutUsType;

import java.util.List;

/**
 * @Author zhang
 * @Date 2023/1/14 17:21
 */
public interface SysAboutUsTypeMapper {
    int insert(SysAboutUsType sysAboutUsType);

    int update(SysAboutUsType sysAboutUsType);

    int delete(Long id);

    List<SysAboutUsType> list();


    List<SysAboutUsType> sysAboutUsTypeDropDown();



}
