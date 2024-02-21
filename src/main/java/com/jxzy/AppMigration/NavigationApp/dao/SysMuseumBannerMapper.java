package com.jxzy.AppMigration.NavigationApp.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jxzy.AppMigration.NavigationApp.entity.SysMuseumBanner;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2023/8/8 10:24
 */
@Mapper
public interface SysMuseumBannerMapper extends BaseMapper<SysMuseumBanner> {
    List<SysMuseumBanner> getMuseumBannerList(Map<String, Object> search);

}
