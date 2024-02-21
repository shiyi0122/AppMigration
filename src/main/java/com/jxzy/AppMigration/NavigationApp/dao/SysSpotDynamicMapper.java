package com.jxzy.AppMigration.NavigationApp.dao;

import com.jxzy.AppMigration.NavigationApp.entity.SysSpotDynamic;

import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2023/1/12 19:14
 */
public interface SysSpotDynamicMapper {
    List<SysSpotDynamic> list(Map<String, Object> search);


    int insertSelective(SysSpotDynamic sysSpotDynamic);


    int updateSelective(SysSpotDynamic sysSpotDynamic);


    int deleteByPrimaryKey(Long id);


    SysSpotDynamic selectById(Long id);

    int addSysSpotDynamicBrowse(Long id);

    SysSpotDynamic selectBySpotId(Long scenicSpotId);

}
