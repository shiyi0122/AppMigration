package com.jxzy.AppMigration.NavigationApp.dao;

import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotBinding;

import java.util.List;
import java.util.Map;

public interface SysScenicSpotBindingMapper {
    int deleteByPrimaryKey(Long scenicSpotFid);

    int insert(SysScenicSpotBinding record);

    int insertSelective(SysScenicSpotBinding record);

    SysScenicSpotBinding selectByPrimaryKey(Long scenicSpotFid);

    int updateByPrimaryKeySelective(SysScenicSpotBinding record);

    int updateByPrimaryKey(SysScenicSpotBinding record);

    /**
     * 获取景区和城市列表
     * @param: search
     * @description: TODO
     * @author: qushaobei
     * @date: 2021/11/1 0001
     */
    List<SysScenicSpotBinding> queryCityAndScenicSpotLists(Map<String, Object> search);
}