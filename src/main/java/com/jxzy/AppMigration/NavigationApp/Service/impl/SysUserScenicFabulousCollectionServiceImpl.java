package com.jxzy.AppMigration.NavigationApp.Service.impl;

import com.github.pagehelper.PageHelper;
import com.jxzy.AppMigration.NavigationApp.Service.SysUserScenicFabulousCollectionService;
import com.jxzy.AppMigration.NavigationApp.dao.SysUserScenicFabulousCollectionMapper;
import com.jxzy.AppMigration.NavigationApp.entity.SysUserScenicFabulousCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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

    /**
     * 查询用户收藏景点列表
     * @param: pageNum
     * @param: pageSize
     * @param: search
     * @description: TODO
     * @return: java.util.List<com.jxzy.AppMigration.NavigationApp.entity.SysUserScenicFabulousCollection>
     * @author: qushaobei
     * @date: 2022/8/15 0015
     */
    public List<SysUserScenicFabulousCollection> queryUserScenicCollection(int pageNum, int pageSize, Map<String, Object> search) {
        PageHelper.startPage(pageNum,pageSize);
        return sysUserScenicFabulousCollectionMapper.queryUserScenicCollection(search);
    }

    /**
     * 查询用户点赞景点列表
     * @param: pageNum
     * @param: pageSize
     * @param: search
     * @description: TODO
     * @return: java.util.List<com.jxzy.AppMigration.NavigationApp.entity.SysUserScenicFabulousCollection>
     * @author: qushaobei
     * @date: 2022/8/15 0015
     */
    public List<SysUserScenicFabulousCollection> queryUserScenicLike(int pageNum, int pageSize, Map<String, Object> search) {
        PageHelper.startPage(pageNum,pageSize);
        return sysUserScenicFabulousCollectionMapper.queryUserScenicLike(search);
    }
}
