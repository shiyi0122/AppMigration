package com.jxzy.AppMigration.NavigationApp.Service;

import com.jxzy.AppMigration.NavigationApp.entity.SysUserScenicFabulousCollection;
import com.jxzy.AppMigration.NavigationApp.util.PageDataResult;

import java.util.List;
import java.util.Map;

public interface SysUserScenicFabulousCollectionService {

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
     * 创建用户景点点赞或收藏
     * @param: scenics
     * @description: TODO
     * @return: int
     * @author: qushaobei
     * @date: 2022/8/3 0003
     */
    int insetUserFabulousCollection(SysUserScenicFabulousCollection scenics);

    /**
     * 更新用户景点点赞或收藏
     * @param: scenic
     * @description: TODO
     * @return: int
     * @author: qushaobei
     * @date: 2022/8/3 0003
     */
    int updateUserFabulousCollection(SysUserScenicFabulousCollection scenic);

    /**
     * 查询用户收藏景点列表
     * @param: pageNum
     * @param: pageSize
     * @param: search
     * @description: TODO
     * @return: java.util.List<com.jxzy.AppMigration.NavigationApp.entity.SysUserScenicFabulousCollection>
     * @author: qushaobei
     * @date: 2022/8/15 0015
     */
    List<SysUserScenicFabulousCollection> queryUserScenicCollection(int pageNum, int pageSize, Map<String, Object> search);

    /**
     * 查询用户点赞景点列表
     * @param: pageNum
     * @param: pageSize
     * @param: search
     * @description: TODO
     * @return: java.util.List<com.jxzy.AppMigration.NavigationApp.entity.SysUserScenicFabulousCollection>
     * @author: qushaobei
     * @date: 2022/8/15 0015
     */
    List<SysUserScenicFabulousCollection> queryUserScenicLike(int pageNum, int pageSize, Map<String, Object> search);

    /**
     * 根据景点id，获取景点收藏数量
     * @param scenicDistrictId
     * @return
     */
    int getScenicSpotCollectionCount(Long scenicDistrictId);

    /**
     * 根据景点id，获取景点点赞数量
     * @param scenicDistrictId
     * @return
     */
    int getScenicSpotFabulousCount(Long scenicDistrictId);

    /**
     * 后台管理--景点点赞收藏查询
     * @param search
     * @return
     */
    PageDataResult getUserScenicFabulousCollectionList(Integer pageNum,Integer pageSize,Map<String, Object> search);

}
