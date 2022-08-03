package com.jxzy.AppMigration.NavigationApp.dao;

import com.jxzy.AppMigration.NavigationApp.entity.SysGuideAppNewsRead;

import java.util.HashMap;
import java.util.List;

/**
 * @Author zhang
 * @Date 2022/7/23 13:26
 */
public interface SysGuideAppNewsReadMapper {


    int insertSelective(SysGuideAppNewsRead sysGuideAppNewsRead) ;

    List<SysGuideAppNewsRead> selectBySearch(HashMap<String, Object> search);

    List<String> selectIdBySearch(HashMap<String, Object> search);

}
