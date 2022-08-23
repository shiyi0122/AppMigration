package com.jxzy.AppMigration.NavigationApp.dao;

import com.jxzy.AppMigration.NavigationApp.entity.SysUserScenicFabulousCollection;

import java.util.List;
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

    /**
     * 查询用户收藏景点列表
     * @param: search
     * @description: TODO
     * @return: java.util.List<com.jxzy.AppMigration.NavigationApp.entity.SysUserScenicFabulousCollection>
     * @author: qushaobei
     * @date: 2022/8/15 0015
     */
    List<SysUserScenicFabulousCollection> queryUserScenicCollection(Map<String, Object> search);

    /**
     * 查询用户点赞景点列表
     * @param: search
     * @description: TODO
     * @return: java.util.List<com.jxzy.AppMigration.NavigationApp.entity.SysUserScenicFabulousCollection>
     * @author: qushaobei
     * @date: 2022/8/15 0015
     */
    List<SysUserScenicFabulousCollection> queryUserScenicLike(Map<String, Object> search);
}