package com.jxzy.AppMigration.NavigationApp.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jxzy.AppMigration.NavigationApp.entity.SysMuseumMapRes;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author zhang
 * @Date 2023/8/7 15:50
 */
@Mapper
public interface SysMuseumMapResMapper extends BaseMapper<SysMuseumMapRes> {
    List<SysMuseumMapRes> selectByMuseumId(Long id);

    List<SysMuseumMapRes> getMuseumMapResByCondition(@Param("museumName") String museumName);

}
