package com.jxzy.AppMigration.NavigationApp.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jxzy.AppMigration.NavigationApp.entity.SysMuseumCollectionBroadcast;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

/**
 * @Author zhang
 * @Date 2023/6/21 13:54
 */
@Mapper
public interface SysMuseumCollectionBroadcastMapper extends BaseMapper<SysMuseumCollectionBroadcast> {

    Integer getMuseumCollectionBroadcastNumber(Long id);


    SysMuseumCollectionBroadcast getCollectionIdBroadcast(Map<String, Object> search);


}
