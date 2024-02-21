package com.jxzy.AppMigration.NavigationApp.Service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jxzy.AppMigration.NavigationApp.Service.SysUserDistrictFabulousCollectionService;
import com.jxzy.AppMigration.NavigationApp.dao.SysScenicSpotHeatMapper;
import com.jxzy.AppMigration.NavigationApp.dao.SysUserDistrictFabulousCollectionMapper;
import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotHeat;
import com.jxzy.AppMigration.NavigationApp.entity.SysUserDistrictFabulousCollection;
import com.jxzy.AppMigration.NavigationApp.util.PageDataResult;
import com.jxzy.AppMigration.common.utils.DateUtil;
import com.jxzy.AppMigration.common.utils.IdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class SysUserDistrictFabulousCollectionServiceImpl implements SysUserDistrictFabulousCollectionService {
    @Autowired
    private SysUserDistrictFabulousCollectionMapper sysUserDistrictFabulousCollectionMapper;
    @Autowired
    private SysScenicSpotHeatMapper sysScenicSpotHeatMapper;

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


        if  (StringUtils.isEmpty(users.getFabulous())){//收藏

            SysScenicSpotHeat sysScenicSpotHeat1 = sysScenicSpotHeatMapper.selectSpotAndTypeByHeat(users.getScenicSpotId(), 4);
            if (StringUtils.isEmpty(sysScenicSpotHeat1)){
                SysScenicSpotHeat sysScenicSpotHeat = new SysScenicSpotHeat();
                sysScenicSpotHeat.setId(IdUtils.getSeqId());
                sysScenicSpotHeat.setCreateTime(DateUtil.currentDateTime());
                sysScenicSpotHeat.setUpdateTime(DateUtil.currentDateTime());
                sysScenicSpotHeat.setScenicSpotId(users.getScenicSpotId());
                sysScenicSpotHeat.setTotal(1);
                sysScenicSpotHeat.setSameDay(1);
                sysScenicSpotHeat.setType("4");
                sysScenicSpotHeatMapper.insertSelective(sysScenicSpotHeat);
            }else{
                sysScenicSpotHeatMapper.addTotal(users.getScenicSpotId(),4);
            }
        }else{//点赞

            SysScenicSpotHeat sysScenicSpotHeat1 = sysScenicSpotHeatMapper.selectSpotAndTypeByHeat(users.getScenicSpotId(), 4);
            if (StringUtils.isEmpty(sysScenicSpotHeat1)){
                SysScenicSpotHeat sysScenicSpotHeat = new SysScenicSpotHeat();
                sysScenicSpotHeat.setId(IdUtils.getSeqId());
                sysScenicSpotHeat.setCreateTime(DateUtil.currentDateTime());
                sysScenicSpotHeat.setUpdateTime(DateUtil.currentDateTime());
                sysScenicSpotHeat.setScenicSpotId(users.getScenicSpotId());
                sysScenicSpotHeat.setTotal(1);
                sysScenicSpotHeat.setSameDay(1);
                sysScenicSpotHeat.setType("5");
                sysScenicSpotHeatMapper.insertSelective(sysScenicSpotHeat);
            }else{
                sysScenicSpotHeatMapper.addTotal(users.getScenicSpotId(),5);
            }
        }

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
    public int updateUserFabulousCollection(SysUserDistrictFabulousCollection user,String part) {

        if ("1".equals(part)){//收藏
            sysScenicSpotHeatMapper.addTotal(user.getScenicSpotId(),5);
        }else if ("2".equals(part)){
            sysScenicSpotHeatMapper.addTotal(user.getScenicSpotId(),4);
        }


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

    /**
     * 根据用户id和景区查询景区是否点赞收藏
     * zhang
     * @param
     * @return
     */
    @Override
    public SysUserDistrictFabulousCollection ifUserLikeCollection(String spotId, String uid) {

        Map<String, Object> search = new HashMap<>();
        search.put("spotId",spotId);
        search.put("uid",uid);
        SysUserDistrictFabulousCollection sysUserDistrictFabulousCollection = sysUserDistrictFabulousCollectionMapper.ifUserLikeCollection(search);
        return sysUserDistrictFabulousCollection;
    }

    /**
     * 后台管理——用户景区点赞收藏列表查询
     * @param pageNum
     * @param pageSize
     * @param search
     * @return
     */
    @Override
    public PageDataResult getSysUserDistrictFabulousList(Integer pageNum, Integer pageSize, Map<String, Object> search) {

        PageDataResult pageDataResult = new PageDataResult();
        PageHelper.startPage(pageNum,pageSize);
        List<SysUserDistrictFabulousCollection> list = sysUserDistrictFabulousCollectionMapper.selectBySearch(search);

        if (list.size()>0){
            PageInfo<SysUserDistrictFabulousCollection> pageInfo = new PageInfo<>(list);
            pageDataResult.setData(list);
            pageDataResult.setCode(200);
            pageDataResult.setTotals((int)pageInfo.getTotal());
        }
        return pageDataResult;
    }

    /**
     * 根据景区id，获取景区的总收藏数量
     * @param scenicSpotId
     * @return
     */
    @Override
    public int getScenicSpotCollectionCount(Long scenicSpotId) {

        Integer spotIdCollectionCount = sysUserDistrictFabulousCollectionMapper.getSpotIdCollectionCount(scenicSpotId);

        return spotIdCollectionCount;

    }

    /**
     * 根据景区id，获取景区的总点赞数量
     * @param scenicSpotId
     * @return
     */
    @Override
    public int getScenicSpotFabulousCount(Long scenicSpotId) {

        Integer spotIdFabulousCount = sysUserDistrictFabulousCollectionMapper.getSpotIdFabulousCount(scenicSpotId);
        return spotIdFabulousCount;

    }

    /**
     * 后台管理——查询点赞收藏
     * @param search
     * @return
     */
    @Override
    public PageDataResult getUserFabulousCollectionList(Integer pageNum,Integer pageSize,Map<String, Object> search) {

        PageDataResult pageDataResult = new PageDataResult();
        PageHelper.startPage(pageNum,pageSize);
        List<SysUserDistrictFabulousCollection> list = sysUserDistrictFabulousCollectionMapper.getUserFabulousCollectionList(search);
        for (SysUserDistrictFabulousCollection sysUserDistrictFabulousCollection : list) {

            Integer spotIdFabulousCount = sysUserDistrictFabulousCollectionMapper.getSpotIdFabulousCount(sysUserDistrictFabulousCollection.getScenicSpotId());
            Integer spotIdCollectionCount = sysUserDistrictFabulousCollectionMapper.getSpotIdCollectionCount(sysUserDistrictFabulousCollection.getScenicSpotId());

            sysUserDistrictFabulousCollection.setCollectionCount(spotIdCollectionCount);
            sysUserDistrictFabulousCollection.setFabulousCount(spotIdFabulousCount);

        }

        if (list.size()>0){
            PageInfo<SysUserDistrictFabulousCollection> pageInfo = new PageInfo<>(list);
            pageDataResult.setData(list);
            pageDataResult.setCode(200);
            pageDataResult.setTotals((int)pageInfo.getTotal());
        }
        return pageDataResult;
    }
}
