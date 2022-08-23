package com.jxzy.AppMigration.NavigationApp.Service.impl;

import com.github.pagehelper.PageHelper;
import com.jxzy.AppMigration.NavigationApp.Service.SysUserDistrictFabulousCollectionService;
import com.jxzy.AppMigration.NavigationApp.dao.SysUserDistrictFabulousCollectionMapper;
import com.jxzy.AppMigration.NavigationApp.entity.SysUserDistrictFabulousCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class SysUserDistrictFabulousCollectionServiceImpl implements SysUserDistrictFabulousCollectionService {
    @Autowired
    private SysUserDistrictFabulousCollectionMapper sysUserDistrictFabulousCollectionMapper;

    /**
     * 查询用户是否在此景区进行点赞或收藏
     * @param: search
     * @description: TODO
     * @return: com.jxzy.AppMigration.NavigationApp.entity.SysUserDistrictFabulousCollection
     * @author: qushaobei
     * @date: 2022/8/3 0003
     */
    public SysUserDistrictFabulousCollection queryUserFabulousCollection(Map<String, Object> search) {
        return sysUserDistrictFabulousCollectionMapper.queryUserFabulousCollection(search);
    }

    /**
     * 创建用户景区点赞或收藏
     * @param: users
     * @description: TODO
     * @return: int
     * @author: qushaobei
     * @date: 2022/8/3 0003
     */
    public int insetUserFabulousCollection(SysUserDistrictFabulousCollection users) {
        return sysUserDistrictFabulousCollectionMapper.insertSelective(users);
    }

    /**
     * 更新用户点赞或收藏
     * @param: user
     * @description: TODO
     * @return: int
     * @author: qushaobei
     * @date: 2022/8/3 0003
     */
    public int updateUserFabulousCollection(SysUserDistrictFabulousCollection user) {
        return sysUserDistrictFabulousCollectionMapper.updateByPrimaryKeySelective(user);
    }

    /**
     * 查询景区收藏列表
     * @param: pageNum
     * @param: pageSize
     * @param: search
     * @description: TODO
     * @return: java.util.List<com.jxzy.AppMigration.NavigationApp.entity.SysUserDistrictFabulousCollection>
     * @author: qushaobei
     * @date: 2022/8/15 0015
     */
    public List<SysUserDistrictFabulousCollection> queryUserCollection(int pageNum, int pageSize, Map<String, Object> search) {
        PageHelper.startPage(pageNum,pageSize);
        return sysUserDistrictFabulousCollectionMapper.queryUserCollection(search);
    }

    /**
     * 查询用户点赞景区列表
     * @param: pageNum
     * @param: pageSize
     * @param: search
     * @description: TODO
     * @return: java.util.List<com.jxzy.AppMigration.NavigationApp.entity.SysUserDistrictFabulousCollection>
     * @author: qushaobei
     * @date: 2022/8/15 0015
     */
    public List<SysUserDistrictFabulousCollection> queryUserLike(int pageNum, int pageSize, Map<String, Object> search) {
        PageHelper.startPage(pageNum,pageSize);
        return sysUserDistrictFabulousCollectionMapper.queryUserLike(search);
    }
}
