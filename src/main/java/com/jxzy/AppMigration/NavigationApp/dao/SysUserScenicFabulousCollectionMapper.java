package com.jxzy.AppMigration.NavigationApp.dao;

import com.jxzy.AppMigration.NavigationApp.entity.SysUserScenicFabulousCollection;

import java.util.Map;

public interface SysUserScenicFabulousCollectionMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SysUserScenicFabulousCollection record);

    int insertSelective(SysUserScenicFabulousCollection record);

    SysUserScenicFabulousCollection selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysUserScenicFabulousCollection record);

    int updateByPrimaryKey(SysUserScenicFabulousCollection record);

    /**
     * 查询用户景点点赞或收藏
     * @param: search
     * @description: TODO
     * @return: com.jxzy.AppMigration.NavigationApp.entity.SysUserScenicFabulousCollection
     * @author: qushaobei
     * @date: 2022/8/3 0003
     */
    SysUserScenicFabulousCollection queryUserFabulousCollection(Map<String, Object> search);
}