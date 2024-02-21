package com.jxzy.AppMigration.NavigationApp.dao;

import com.jxzy.AppMigration.NavigationApp.entity.SysHotelDetails;

import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2023/4/24 14:24
 */
public interface SysHotelDetailsMapper {
    List<SysHotelDetails> getSysHotelIdDetails(Map<String, Object> search);

}
