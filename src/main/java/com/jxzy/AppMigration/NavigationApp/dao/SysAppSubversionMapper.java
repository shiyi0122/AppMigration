package com.jxzy.AppMigration.NavigationApp.dao;

import com.jxzy.AppMigration.NavigationApp.entity.SysAppSubversion;

import java.util.List;

/**
 * @Author zhang
 * @Date 2023/4/26 9:47
 */
public interface SysAppSubversionMapper {

    int insert(SysAppSubversion sysAppSubversions);


    int update(SysAppSubversion sysAppSubversions);

    int delSysAppSubversion(Long id);

    List<SysAppSubversion> getSysAppSubversionList();


    List<SysAppSubversion> sysAppSubversionDropDown();


}
