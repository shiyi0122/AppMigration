package com.jxzy.AppMigration.NavigationApp.dao;

import com.jxzy.AppMigration.NavigationApp.entity.SysStrategyContent;

import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2023/1/18 10:36
 */
public interface SysStrategyContentMapper {

    int insert(SysStrategyContent sysStrategyContent);

    int delete(Long id);

    List<SysStrategyContent> getStrategyIdByList(Map<String, Object> search);

    List<SysStrategyContent> getStrategyIdByListN(Long id);

    int deleteStrategyId(Long id);

    int updateSelective(SysStrategyContent sysStrategyContent);

    int delSysStrategyContentPic(Long id);

}
