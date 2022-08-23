package com.jxzy.AppMigration.NavigationApp.dao;

import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotShopsType;

public interface SysScenicSpotShopsTypeMapper {
    int deleteByPrimaryKey(Long typeId);

    int insert(SysScenicSpotShopsType record);

    int insertSelective(SysScenicSpotShopsType record);

    SysScenicSpotShopsType selectByPrimaryKey(Long typeId);

    int updateByPrimaryKeySelective(SysScenicSpotShopsType record);

    int updateByPrimaryKey(SysScenicSpotShopsType record);
}