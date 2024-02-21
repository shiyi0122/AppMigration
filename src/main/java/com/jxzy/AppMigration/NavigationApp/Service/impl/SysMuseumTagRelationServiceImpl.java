package com.jxzy.AppMigration.NavigationApp.Service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jxzy.AppMigration.NavigationApp.Service.SysMuseumTagRelationService;
import com.jxzy.AppMigration.NavigationApp.dao.SysMuseumTagRelationMapper;
import com.jxzy.AppMigration.NavigationApp.entity.SysMuseumTagRelation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author libofan
 * @since 2023-10-30
 */
@Service
public class SysMuseumTagRelationServiceImpl extends ServiceImpl<SysMuseumTagRelationMapper, SysMuseumTagRelation> implements SysMuseumTagRelationService {
    @Autowired
    private SysMuseumTagRelationMapper sysMuseumTagRelationMapper;
    @Override
    public boolean saveBatch(Collection<SysMuseumTagRelation> entityList) {
        return super.saveBatch(entityList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean delMuseumTagForMuseum(List<SysMuseumTagRelation> relations) {

        try {
            for (SysMuseumTagRelation relation : relations) {
                sysMuseumTagRelationMapper.delMuseumTagForMuseum(relation);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
