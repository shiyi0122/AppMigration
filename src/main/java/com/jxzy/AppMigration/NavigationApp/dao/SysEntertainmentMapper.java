package com.jxzy.AppMigration.NavigationApp.dao;

import com.jxzy.AppMigration.NavigationApp.entity.SysEntertainment;
import com.jxzy.AppMigration.NavigationApp.entity.SysGame;

import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2023/1/11 17:18
 */
public interface SysEntertainmentMapper {
    int insertSelective(SysEntertainment sysEntertainment);

    int updateSelective(SysEntertainment sysEntertainment);

    int deleteByPrimaryKey(Long id);

    List<SysEntertainment> list(Map<String, Object> search);

    List<SysEntertainment> listEntertainment(Map<String, Object> search);

    List<SysEntertainment> uploadExcelEntertainment(Map<String, Object> search);

    SysEntertainment selectById(String id);

}
