package com.jxzy.AppMigration.NavigationApp.dao;

import com.jxzy.AppMigration.NavigationApp.entity.SysHotel;
import com.jxzy.AppMigration.NavigationApp.entity.SysNavigation;

import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2023/1/11 15:40
 */
public interface SysHotelMapper {
    int insertSelective(SysHotel sysHotel);

    int updateSelective(SysHotel sysHotel);

    int deleteByPrimaryKey(Long id);

    List<SysHotel> list(Map<String, Object> search);


    /**
     * 导出
     * @param search
     * @return
     */
    List<SysHotel> uploadExcelHotel(Map<String, Object> search);

    /**
     * 根据id查询详细
     * @param id
     * @return
     */
    SysHotel selectById(String id);


}
