package com.jxzy.AppMigration.NavigationApp.dao;

import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotShopsType;

import java.util.List;
import java.util.Map;

public interface SysScenicSpotShopsTypeMapper {
    int deleteByPrimaryKey(Long typeId);

    int insert(SysScenicSpotShopsType record);

    int insertSelective(SysScenicSpotShopsType record);

    SysScenicSpotShopsType selectByPrimaryKey(Long typeId);

    int updateByPrimaryKeySelective(SysScenicSpotShopsType record);

    int updateByPrimaryKey(SysScenicSpotShopsType record);

    List<SysScenicSpotShopsType> selectBySearch(Map<String, Object> search);
}