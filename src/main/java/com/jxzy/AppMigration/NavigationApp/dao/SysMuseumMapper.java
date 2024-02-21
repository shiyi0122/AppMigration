package com.jxzy.AppMigration.NavigationApp.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jxzy.AppMigration.NavigationApp.entity.SysMuseum;
import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotGpsCoordinateWithBLOBs;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2023/6/20 11:15
 */
@Mapper
public interface SysMuseumMapper extends BaseMapper<SysMuseum> {

    List<SysMuseum> getSysMuseumList(Map<String, Object> search);

    SysMuseum getSysMuseumIdDetails(Map<String, Object> search);
}