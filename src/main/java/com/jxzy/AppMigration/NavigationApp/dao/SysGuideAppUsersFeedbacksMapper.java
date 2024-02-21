package com.jxzy.AppMigration.NavigationApp.dao;

import com.jxzy.AppMigration.NavigationApp.entity.SysGuideAppUsersFeedbacks;

import java.util.List;
import java.util.Map;

public interface SysGuideAppUsersFeedbacksMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SysGuideAppUsersFeedbacks record);

    int insertSelective(SysGuideAppUsersFeedbacks record);

    SysGuideAppUsersFeedbacks selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysGuideAppUsersFeedbacks record);

    int updateByPrimaryKey(SysGuideAppUsersFeedbacks record);

    List<SysGuideAppUsersFeedbacks> selectBySearch(Map<String, Object> search);

}