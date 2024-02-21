package com.jxzy.AppMigration.NavigationApp.Service;

import com.jxzy.AppMigration.NavigationApp.entity.SysAboutUsType;
import com.jxzy.AppMigration.NavigationApp.entity.SysAppSubversion;
import com.jxzy.AppMigration.NavigationApp.util.PageDataResult;

import java.util.List;

/**
 * @Author zhang
 * @Date 2023/4/26 9:43
 */
public interface SysAppSubversionService {

    int addSysAppSubversion(SysAppSubversion sysAppSubversions);

    int editSysAppSubversion(SysAppSubversion sysAppSubversions);

    int delSysAppSubversion(Long id);

    PageDataResult getSysAppSubversionList(Integer pageNum, Integer pageSize);

    List<SysAppSubversion>  sysAppSubversionDropDown();


}
