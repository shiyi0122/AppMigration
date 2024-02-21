package com.jxzy.AppMigration.NavigationApp.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jxzy.AppMigration.NavigationApp.entity.SysMuseumTagRelation;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author libofan
 * @since 2023-10-30
 */
public interface SysMuseumTagRelationService extends IService<SysMuseumTagRelation> {
    /**
     * 根据博物馆id和标签id，(可批量)删除博物馆标签
     * @param relations
     * @return
     */
   boolean delMuseumTagForMuseum(List<SysMuseumTagRelation> relations);
}
