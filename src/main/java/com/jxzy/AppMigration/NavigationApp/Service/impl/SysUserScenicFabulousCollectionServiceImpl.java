package com.jxzy.AppMigration.NavigationApp.Service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jxzy.AppMigration.NavigationApp.Service.SysUserScenicFabulousCollectionService;
import com.jxzy.AppMigration.NavigationApp.dao.SysUserScenicFabulousCollectionMapper;
import com.jxzy.AppMigration.NavigationApp.entity.SysUserScenicFabulousCollection;
import com.jxzy.AppMigration.NavigationApp.util.PageDataResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.ref.PhantomReference;
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

    /**
     * 根据景点id，获取景点收藏数量
     *
     */
    @Override
    public int getScenicSpotCollectionCount(Long scenicDistrictId) {

        Integer spotIdCollectionCount = sysUserScenicFabulousCollectionMapper.getSpotIdCollectionCount(scenicDistrictId);
        return spotIdCollectionCount;

    }
    /**
     * 根据景点id，获取景点收藏数量
     */
    @Override
    public int getScenicSpotFabulousCount(Long scenicDistrictId) {

        Integer spotIdFabulousCount = sysUserScenicFabulousCollectionMapper.getSpotIdFabulousCount(scenicDistrictId);
        return spotIdFabulousCount;

    }

    /**
     * 后台管理--景点点赞收藏列表查询
     * @param search
     * @return
     */
    @Override
    public PageDataResult getUserScenicFabulousCollectionList(Integer pageNum,Integer pageSize,Map<String, Object> search) {

        PageDataResult pageDataResult = new PageDataResult();
        PageHelper.startPage(pageNum,pageSize);
        List<SysUserScenicFabulousCollection> list = sysUserScenicFabulousCollectionMapper.getUserScenicFabulousCollectionList(search);
        for (SysUserScenicFabulousCollection sysUserScenicFabulousCollection : list) {

            Integer spotIdFabulousCount = sysUserScenicFabulousCollectionMapper.getSpotIdFabulousCount(sysUserScenicFabulousCollection.getScenicDistrictId());
            Integer spotIdCollectionCount = sysUserScenicFabulousCollectionMapper.getSpotIdCollectionCount(sysUserScenicFabulousCollection.getScenicDistrictId());

            sysUserScenicFabulousCollection.setCollectionCount(spotIdCollectionCount);
            sysUserScenicFabulousCollection.setFabulousCount(spotIdFabulousCount);

        }

        if (list.size()>0){
            PageInfo<SysUserScenicFabulousCollection> pageInfo = new PageInfo<>(list);
            pageDataResult.setData(list);
            pageDataResult.setCode(200);
            pageDataResult.setTotals((int) pageInfo.getTotal());
        }

        return pageDataResult;

    }
}
