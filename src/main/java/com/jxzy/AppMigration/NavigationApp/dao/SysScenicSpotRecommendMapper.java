package com.jxzy.AppMigration.NavigationApp.dao;

import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotRecommend;

import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2023/4/3 19:20
 */
public interface SysScenicSpotRecommendMapper {
    List<SysScenicSpotRecommend> sysScenicSpotRecommendList(Map<String, Object> search);


    int insertSelective(SysScenicSpotRecommend sysScenicSpotRecommend);


    int updateByPrimaryKeySelective(SysScenicSpotRecommend sysScenicSpotRecommend);


    int deleteByPrimaryKey(Long id);

    List<String> getSortList();


    SysScenicSpotRecommend getSpotId(Long recommendSpotId);

}
