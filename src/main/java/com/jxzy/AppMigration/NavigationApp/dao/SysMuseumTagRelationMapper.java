package com.jxzy.AppMigration.NavigationApp.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jxzy.AppMigration.NavigationApp.entity.SysMuseumTagRelation;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author libofan
 * @since 2023-10-30
 */
@Mapper
public interface SysMuseumTagRelationMapper extends BaseMapper<SysMuseumTagRelation> {
    /**
     * 根据博物馆id和标签id，删除博物馆标签
     * @param relation
     * @return
     */
    int delMuseumTagForMuseum(SysMuseumTagRelation relation);
}