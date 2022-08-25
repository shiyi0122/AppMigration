package com.jxzy.AppMigration.NavigationApp.dao;

import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotUserFootprint;

import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2022/8/16 16:42
 */
public interface SysScenicSpotUserFootprintMapper {

    List<SysScenicSpotUserFootprint> selectBySearch(Map<String, Object> search);

    int addScenicSpotUserFootprint(SysScenicSpotUserFootprint sysScenicSpotUserFootprint);


    int editScenicSpotUserFootprint(SysScenicSpotUserFootprint sysScenicSpotUserFootprint);



}
