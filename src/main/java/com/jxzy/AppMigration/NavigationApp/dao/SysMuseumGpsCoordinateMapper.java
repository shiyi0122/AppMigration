package com.jxzy.AppMigration.NavigationApp.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jxzy.AppMigration.NavigationApp.entity.SysMuseumGpsCoordinate;
import com.jxzy.AppMigration.NavigationApp.entity.SysMuseumGpsCoordinateWithBLOBs;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author zhang
 * @Date 2023/8/7 14:38
 */
@Mapper
public interface SysMuseumGpsCoordinateMapper extends BaseMapper<SysMuseumGpsCoordinate> {


    List<SysMuseumGpsCoordinateWithBLOBs> selectAll();


}
