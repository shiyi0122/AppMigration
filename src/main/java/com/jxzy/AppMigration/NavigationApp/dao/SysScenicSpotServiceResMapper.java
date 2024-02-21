package com.jxzy.AppMigration.NavigationApp.dao;

import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotServiceRes;

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

    /**
     * 后台管理——查询卫生间列表
     * @param search
     * @return
     */
    List<SysScenicSpotServiceRes> getSpotServiceResList(Map<String, Object> search);

    /**
     * 根据景区id和服务项id查询数据
     * @param scenicSpotId
     * @param serviceId
     * @return
     */
    SysScenicSpotServiceRes getSpotIdAndServiceResId(Long scenicSpotId, Long serviceId);


}