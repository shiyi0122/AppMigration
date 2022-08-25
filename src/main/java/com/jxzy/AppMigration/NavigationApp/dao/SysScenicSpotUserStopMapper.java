package com.jxzy.AppMigration.NavigationApp.dao;

import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotUserStop;

import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2022/8/17 11:10
 */



public interface SysScenicSpotUserStopMapper {


    List<SysScenicSpotUserStop> selectBySearch(Map<String, Object> search);


    int exitSpotUserStop(SysScenicSpotUserStop sysScenicSpotUserStopNew);


    int addSpotUserStop(SysScenicSpotUserStop sysScenicSpotUserStop);


}
