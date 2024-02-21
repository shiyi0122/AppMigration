package com.jxzy.AppMigration.NavigationApp.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jxzy.AppMigration.NavigationApp.entity.SysMuseumCollection;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2023/6/20 13:44
 */
@Mapper
public interface SysMuseumCollectionMapper extends BaseMapper<SysMuseumCollection> {
    Integer getMuseumCollectionNumber(Long id);
    List<SysMuseumCollection> selectMuseumIdByList(Long id);
    List<SysMuseumCollection> getMuseumCollectionIdDetails(Map<String, Object> search);

    /**
     * 根据博物馆名称和藏品名称模糊查询
     * @param map
     * @return
     */
    List<SysMuseumCollection>  selectCollectionByCondition(Map<String,Object> map);
}
