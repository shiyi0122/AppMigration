package com.jxzy.AppMigration.NavigationApp.Service;

import com.jxzy.AppMigration.NavigationApp.entity.SysAboutUsType;
import com.jxzy.AppMigration.NavigationApp.util.PageDataResult;

import java.util.List;

/**
 * @Author zhang
 * @Date 2023/1/14 17:20
 */
public interface SysAboutUsTypeService {
    int addSysAboutUsType(SysAboutUsType sysAboutUsType);

    int editSysAboutUsType(SysAboutUsType sysAboutUsType);

    int delSysAboutUsType(Long id);

    PageDataResult getSysAboutUsTypeList(Integer pageNum, Integer pageSize);


    List<SysAboutUsType> sysAboutUsTypeDropDown();
}
