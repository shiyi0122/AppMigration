package com.jxzy.AppMigration.NavigationApp.Service.impl;

import com.jxzy.AppMigration.NavigationApp.Service.SysUserScenicFabulousCollectionService;
import com.jxzy.AppMigration.NavigationApp.dao.SysUserScenicFabulousCollectionMapper;
import com.jxzy.AppMigration.NavigationApp.entity.SysUserScenicFabulousCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@Transactional
public class SysUserScenicFabulousCollectionServiceImpl implements SysUserScenicFabulousCollectionService {
    @Autowired
    private SysUserScenicFabulousCollectionMapper sysUserScenicFabulousCollectionMapper;

    /**
     * 查询用户景点点赞或收藏
     * @param: search
     * @description: TODO
     * @return: com.jxzy.AppMigration.NavigationApp.entity.SysUserScenicFabulousCollection
     * @author: qushaobei
     * @date: 2022/8/3 0003
     */
    public SysUserScenicFabulousCollection queryUserFabulousCollection(Map<String, Object> search) {
        return sysUserScenicFabulousCollectionMapper.queryUserFabulousCollection(search);
    }

    /**
     * 创建用户景点点赞或收藏
     * @param: scenics
     * @description: TODO
     * @return: int
     * @author: qushaobei
     * @date: 2022/8/3 0003
     */
    public int insetUserFabulousCollection(SysUserScenicFabulousCollection scenics) {
        return sysUserScenicFabulousCollectionMapper.insertSelective(scenics);
    }

    /**
     * 更新用户景点点赞或收藏
     * @param: scenic
     * @description: TODO
     * @return: int
     * @author: qushaobei
     * @date: 2022/8/3 0003
     */
    public int updateUserFabulousCollection(SysUserScenicFabulousCollection scenic) {
        return sysUserScenicFabulousCollectionMapper.updateByPrimaryKeySelective(scenic);
    }
}
