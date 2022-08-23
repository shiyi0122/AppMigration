package com.jxzy.AppMigration.NavigationApp.dao;

import com.jxzy.AppMigration.NavigationApp.entity.SysUserDistrictFabulousCollection;

import java.util.List;
import java.util.Map;

public interface SysUserDistrictFabulousCollectionMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SysUserDistrictFabulousCollection record);

    int insertSelective(SysUserDistrictFabulousCollection record);

    SysUserDistrictFabulousCollection selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysUserDistrictFabulousCollection record);

    int updateByPrimaryKey(SysUserDistrictFabulousCollection record);

    /**
     * 查询用户是否在此景区进行点赞或收藏
     * @param: search
     * @description: TODO
     * @return: com.jxzy.AppMigration.NavigationApp.entity.SysUserDistrictFabulousCollection
     * @author: qushaobei
     * @date: 2022/8/3 0003
     */
    SysUserDistrictFabulousCollection queryUserFabulousCollection(Map<String, Object> search);

    /**
     * 查询用户收藏景区列表
     * @param: search
     * @description: TODO
     * @return: java.util.List<com.jxzy.AppMigration.NavigationApp.entity.SysUserDistrictFabulousCollection>
     * @author: qushaobei
     * @date: 2022/8/15 0015
     */
    List<SysUserDistrictFabulousCollection> queryUserCollection(Map<String, Object> search);

    /**
     * 查询用户点赞景区列表
     * @param: search
     * @description: TODO
     * @return: java.util.List<com.jxzy.AppMigration.NavigationApp.entity.SysUserDistrictFabulousCollection>
     * @author: qushaobei
     * @date: 2022/8/15 0015
     */
    List<SysUserDistrictFabulousCollection> queryUserLike(Map<String, Object> search);
}