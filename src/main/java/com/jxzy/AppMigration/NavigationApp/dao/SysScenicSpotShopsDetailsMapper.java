package com.jxzy.AppMigration.NavigationApp.dao;

import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotShopsDetails;

import java.util.List;
import java.util.Map;

public interface SysScenicSpotShopsDetailsMapper {
    int deleteByPrimaryKey(Long detailsId);

    int insert(SysScenicSpotShopsDetails record);

    int insertSelective(SysScenicSpotShopsDetails record);

    SysScenicSpotShopsDetails selectByPrimaryKey(Long detailsId);

    int updateByPrimaryKeySelective(SysScenicSpotShopsDetails record);

    int updateByPrimaryKey(SysScenicSpotShopsDetails record);

    List<SysScenicSpotShopsDetails> getSpotShopsDetails(Map<String, Object> search);

    int deleteByPrimaryTypeId(Long typeId);

}