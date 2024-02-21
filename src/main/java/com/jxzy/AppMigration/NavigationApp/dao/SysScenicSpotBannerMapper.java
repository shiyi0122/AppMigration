package com.jxzy.AppMigration.NavigationApp.dao;

import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotBanner;

import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2022/7/30 13:50
 */
public interface SysScenicSpotBannerMapper {


    List<SysScenicSpotBanner> getScenicSpotBanner(Map<String, Object> search);


    int addScenicSpotBanner(SysScenicSpotBanner sysScenicSpotBanner);

    int editScenicSpotBanner(SysScenicSpotBanner sysScenicSpotBanner);


    int delSpotFilesBanner(String id);

    List<SysScenicSpotBanner> selectSpotIdByList(Long scenicSpotId);


    /**
     * 获取景区轮播图数量
     * @param scenicSpotId
     * @return
     */
    Integer getScenicSpotBannerCount(Long scenicSpotId);

    /**
     * 后台管理—— 修改轮播图启用禁用状态
     * @param id
     * @return
     */
    SysScenicSpotBanner selectByPrimaryKey(Long id);

}
