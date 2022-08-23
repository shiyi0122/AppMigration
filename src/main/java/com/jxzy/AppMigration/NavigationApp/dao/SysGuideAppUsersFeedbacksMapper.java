package com.jxzy.AppMigration.NavigationApp.dao;

import com.jxzy.AppMigration.NavigationApp.entity.SysGuideAppUsersFeedbacks;

public interface SysGuideAppUsersFeedbacksMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SysGuideAppUsersFeedbacks record);

    int insertSelective(SysGuideAppUsersFeedbacks record);

    SysGuideAppUsersFeedbacks selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysGuideAppUsersFeedbacks record);

    int updateByPrimaryKey(SysGuideAppUsersFeedbacks record);
}