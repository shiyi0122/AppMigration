package com.jxzy.AppMigration.NavigationApp.dao;

import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotServiceRes;
import sun.java2d.pipe.AAShapePipe;

import java.util.List;
import java.util.Map;

public interface SysScenicSpotServiceResMapper {
    int deleteByPrimaryKey(Long serviceId);

    int insert(SysScenicSpotServiceRes record);

    int insertSelective(SysScenicSpotServiceRes record);

    SysScenicSpotServiceRes selectByPrimaryKey(Long serviceId);

    int updateByPrimaryKeySelective(SysScenicSpotServiceRes record);

    int updateByPrimaryKey(SysScenicSpotServiceRes record);

    /**
     * 查询洗手间列表
     * @param: search
     * @description: TODO
     * @author: qushaobei
     * @date: 2021/11/4 0004
     */
    List<SysScenicSpotServiceRes> queryToiletList(Map<String, Object> search);
}