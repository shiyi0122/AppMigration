package com.jxzy.AppMigration.NavigationApp.dao;

import com.jxzy.AppMigration.NavigationApp.entity.SysEntertainment;
import com.jxzy.AppMigration.NavigationApp.entity.SysGame;

import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2023/1/11 17:18
 */
public interface SysGameMapper {
    int insertSelective(SysGame sysGame);

    int updateSelective(SysGame sysGame);

    int deleteByPrimaryKey(Long id);

    List<SysGame> list(Map<String, Object> search);

    List<SysEntertainment> listEntertainment(Map<String, Object> search);

}
