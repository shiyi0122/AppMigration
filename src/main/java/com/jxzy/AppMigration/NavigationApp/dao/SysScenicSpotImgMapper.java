package com.jxzy.AppMigration.NavigationApp.dao;

import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotImg;

public interface SysScenicSpotImgMapper {
    int deleteByPrimaryKey(Long scneicSpotImgId);

    int insert(SysScenicSpotImg record);

    int insertSelective(SysScenicSpotImg record);

    SysScenicSpotImg selectByPrimaryKey(Long scneicSpotImgId);

    int updateByPrimaryKeySelective(SysScenicSpotImg record);

    int updateByPrimaryKey(SysScenicSpotImg record);
}