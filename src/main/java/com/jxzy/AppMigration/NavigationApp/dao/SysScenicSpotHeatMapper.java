package com.jxzy.AppMigration.NavigationApp.dao;

import com.jxzy.AppMigration.NavigationApp.Service.SysScenicSpotHeatService;
import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotHeat;

import java.util.Map;

public interface SysScenicSpotHeatMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SysScenicSpotHeat record);

    int insertSelective(SysScenicSpotHeat record);

    SysScenicSpotHeat selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysScenicSpotHeat record);

    int updateByPrimaryKey(SysScenicSpotHeat record);

    /**
     * 查询最佳人气榜数据
     * @param: search
     * @description: TODO
     * @return: com.jxzy.AppMigration.NavigationApp.Service.SysScenicSpotHeatService
     * @author: qushaobei
     * @date: 2022/8/2 0002
     */
    SysScenicSpotHeat querybestPopularity(Map<String, Object> search);
}