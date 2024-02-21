package com.jxzy.AppMigration.NavigationApp.Service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jxzy.AppMigration.NavigationApp.Service.SysScenicSpotRecommendService;
import com.jxzy.AppMigration.NavigationApp.dao.SysScenicSpotRecommendMapper;
import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotRecommend;
import com.jxzy.AppMigration.NavigationApp.util.PageDataResult;
import com.jxzy.AppMigration.common.utils.DateUtil;
import com.jxzy.AppMigration.common.utils.IdUtils;
import org.omg.PortableServer.ID_UNIQUENESS_POLICY_ID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2023/4/3 19:19
 */
@Service
public class SysScenicSpotRecommendServiceImpl implements SysScenicSpotRecommendService {

    @Autowired
    SysScenicSpotRecommendMapper sysScenicSpotRecommendMapper;


    @Override
    public PageDataResult sysScenicSpotRecommendList(Integer pageNum, Integer pageSize, Map<String, Object> search) {

        PageDataResult pageDataResult = new PageDataResult();

        PageHelper.startPage(pageNum,pageSize);
        List<SysScenicSpotRecommend> list = sysScenicSpotRecommendMapper.sysScenicSpotRecommendList(search);

        if (list.size()>0){
            PageInfo<SysScenicSpotRecommend> pageInfo = new PageInfo<>(list);
            pageDataResult.setData(list);
            pageDataResult.setTotals((int)pageInfo.getTotal());
            pageDataResult.setCode(200);
        }

        return pageDataResult;

    }

    @Override
    public PageDataResult getSpotRecommendList(Integer pageNum, Integer pageSize, Map<String, Object> search) {

        PageDataResult pageDataResult = new PageDataResult();

        PageHelper.startPage(pageNum,pageSize);
        List<SysScenicSpotRecommend> list = sysScenicSpotRecommendMapper.sysScenicSpotRecommendList(search);

        if (list.size()>0){
            PageInfo<SysScenicSpotRecommend> pageInfo = new PageInfo<>(list);
            pageDataResult.setData(list);
            pageDataResult.setTotals((int)pageInfo.getTotal());
            pageDataResult.setCode(200);
        }

        return pageDataResult;


    }

    @Override
    public int addSpotRecommend(SysScenicSpotRecommend sysScenicSpotRecommend) {

        List<String> sortList = sysScenicSpotRecommendMapper.getSortList();

        boolean contains = sortList.contains(sysScenicSpotRecommend.getSort());
        if (contains){
            return 2;
        }else{
            SysScenicSpotRecommend sysScenicSpotRecommend1 = sysScenicSpotRecommendMapper.getSpotId(sysScenicSpotRecommend.getRecommendSpotId());

            if (StringUtils.isEmpty(sysScenicSpotRecommend1)){
                sysScenicSpotRecommend.setId(IdUtils.getSeqId());
                sysScenicSpotRecommend.setCreateTime(DateUtil.currentDateTime());
                sysScenicSpotRecommend.setUpdateTime(DateUtil.currentDateTime());
                int i = sysScenicSpotRecommendMapper.insertSelective(sysScenicSpotRecommend);
                return i;
            }else{
                return 2;
            }

        }

    }

    @Override
    public int editSpotRecommend(SysScenicSpotRecommend sysScenicSpotRecommend) {


        List<String> sortList = sysScenicSpotRecommendMapper.getSortList();

        boolean contains = sortList.contains(sysScenicSpotRecommend.getSort());
        if (contains){
            return 2;
        }else {
            sysScenicSpotRecommend.setUpdateTime(DateUtil.currentDateTime());
            int i = sysScenicSpotRecommendMapper.updateByPrimaryKeySelective(sysScenicSpotRecommend);
            return i;
        }
    }

    @Override
    public int delSpotRecommend(Long id) {

        int i = sysScenicSpotRecommendMapper.deleteByPrimaryKey(id);
        return i;
    }
}
