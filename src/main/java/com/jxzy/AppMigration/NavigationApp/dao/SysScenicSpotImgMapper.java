package com.jxzy.AppMigration.NavigationApp.dao;

import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotImg;

import java.util.Map;

public interface SysScenicSpotImgMapper {
    int deleteByPrimaryKey(Long scneicSpotImgId);

    int insert(SysScenicSpotImg record);

    int insertSelective(SysScenicSpotImg record);

    SysScenicSpotImg selectByPrimaryKey(Long scneicSpotImgId);

    int updateByPrimaryKeySelective(SysScenicSpotImg record);

    int updateByPrimaryKey(SysScenicSpotImg record);

    SysScenicSpotImg selectBySearch(Map<String, Object> search);


    SysScenicSpotImg getScenicSpotImgByScenicSpotId(Long scenicSpotId);

}