package com.jxzy.AppMigration.NavigationApp.dao;

import com.jxzy.AppMigration.NavigationApp.entity.SysSpotDynamicBanner;

import java.util.List;

/**
 * @Author zhang
 * @Date 2023/1/17 16:56
 */
public interface SysSpotDynamicBannerMapper {
    int insertSelective(SysSpotDynamicBanner sysSpotDynamicBanner);

    int updateSelective(SysSpotDynamicBanner sysSpotDynamicBanner);

    int delete(Long id);

    List<SysSpotDynamicBanner> list(Long id);
}
