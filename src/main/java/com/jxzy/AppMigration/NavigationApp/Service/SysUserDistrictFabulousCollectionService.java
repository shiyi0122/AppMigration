package com.jxzy.AppMigration.NavigationApp.Service;

import com.jxzy.AppMigration.NavigationApp.entity.SysUserDistrictFabulousCollection;
import com.jxzy.AppMigration.NavigationApp.util.PageDataResult;

import java.util.List;
import java.util.Map;

public interface SysUserDistrictFabulousCollectionService {

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
     * 创建用户景区点赞或收藏
     * @param: users
     * @description: TODO
     * @return: int
     * @author: qushaobei
     * @date: 2022/8/3 0003
     */
    int insetUserFabulousCollection(SysUserDistrictFabulousCollection users);

    /**
     * 更新用户点赞或收藏
     * @param: user
     * @description: TODO
     * @return: int
     * @author: qushaobei
     * @date: 2022/8/3 0003
     */
    int updateUserFabulousCollection(SysUserDistrictFabulousCollection user,String part);

    /**
     * 查询用户收藏景区列表
     * @param: pageNum
     * @param: pageSize
     * @param: search
     * @description: TODO
     * @return: java.util.List<com.jxzy.AppMigration.NavigationApp.entity.SysUserDistrictFabulousCollection>
     * @author: qushaobei
     * @date: 2022/8/15 0015
     */
    List<SysUserDistrictFabulousCollection> queryUserCollection(int pageNum, int pageSize, Map<String, Object> search);

    /**
     * 查询用户点赞景区列表
     * @param: pageNum
     * @param: pageSize
     * @param: search
     * @description: TODO
     * @return: java.util.List<com.jxzy.AppMigration.NavigationApp.entity.SysUserDistrictFabulousCollection>
     * @author: qushaobei
     * @date: 2022/8/15 0015
     */
    List<SysUserDistrictFabulousCollection> queryUserLike(int pageNum, int pageSize, Map<String, Object> search);

    /**
     * 根据用户id和景区查询景区是否点赞收藏
     * zhang
     * @param
     * @return
     */
    SysUserDistrictFabulousCollection ifUserLikeCollection(String spotId, String uid);

    /**
     * 后台管理——用户景区点赞收藏管理列表查询
     * @param pageNum
     * @param pageSize
     * @param search
     * @return
     */
    PageDataResult getSysUserDistrictFabulousList(Integer pageNum, Integer pageSize, Map<String, Object> search);

    /**
     * 根据景区id，获取景区的总收藏数量
     * @param scenicSpotId
     * @return
     */
    int getScenicSpotCollectionCount(Long scenicSpotId);

    /**
     * 根据景区id，获取景区总点赞数量
     * @param scenicSpotId
     * @return
     */
    int getScenicSpotFabulousCount(Long scenicSpotId);

    /**
     * 查询景区点赞收藏
     * @param search
     * @return
     */
    PageDataResult getUserFabulousCollectionList(Integer pageNum,Integer pageSize,Map<String, Object> search);

}
