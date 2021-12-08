package com.jxzy.AppMigration.NavigationApp.dao;

import com.jxzy.AppMigration.NavigationApp.entity.SysGuideAppUsersFeedback;

public interface SysGuideAppUsersFeedbackMapper {
    int deleteByPrimaryKey(Long feedbackId);

    int insert(SysGuideAppUsersFeedback record);

    int insertSelective(SysGuideAppUsersFeedback record);

    SysGuideAppUsersFeedback selectByPrimaryKey(Long feedbackId);

    int updateByPrimaryKeySelective(SysGuideAppUsersFeedback record);

    int updateByPrimaryKey(SysGuideAppUsersFeedback record);
}