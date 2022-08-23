package com.jxzy.AppMigration.NavigationApp.dao;

import com.jxzy.AppMigration.NavigationApp.entity.SysConfigs;

public interface SysConfigsMapper {
    int deleteByPrimaryKey(Long configsId);

    int insert(SysConfigs record);

    int insertSelective(SysConfigs record);

    SysConfigs selectByPrimaryKey(Long configsId);

    int updateByPrimaryKeySelective(SysConfigs record);

    int updateByPrimaryKeyWithBLOBs(SysConfigs record);

    int updateByPrimaryKey(SysConfigs record);

}