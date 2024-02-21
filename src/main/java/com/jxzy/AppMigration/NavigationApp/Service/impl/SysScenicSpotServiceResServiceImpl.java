package com.jxzy.AppMigration.NavigationApp.Service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jxzy.AppMigration.NavigationApp.Service.SysScenicSpotServiceResService;
import com.jxzy.AppMigration.NavigationApp.dao.SysScenicSpotServiceResMapper;
import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotServiceRes;
import com.jxzy.AppMigration.NavigationApp.util.PageDataResult;
import com.jxzy.AppMigration.NavigationApp.util.Tinypinyin;
import com.jxzy.AppMigration.common.utils.DateUtil;
import com.jxzy.AppMigration.common.utils.IdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class SysScenicSpotServiceResServiceImpl implements SysScenicSpotServiceResService {
    @Autowired
    private SysScenicSpotServiceResMapper sysScenicSpotServiceResMapper;
    /**
     * 查询洗手间列表
     * @param: pageNum
     * @param: pageSize
     * @param: search
     * @description: TODO
     * @author: qushaobei
     * @date: 2021/11/4 0004
     */
    public List<SysScenicSpotServiceRes> queryToiletList(int pageNum, int pageSize, Map<String, Object> search) {
        PageHelper.startPage(pageNum,pageSize);
        return sysScenicSpotServiceResMapper.queryToiletList(search);
    }

    /**
     * 后台管理——卫生间列表查询
     * @param pageNum
     * @param pageSize
     * @param search
     * @return
     */
    @Override
    public PageDataResult getSpotServiceResList(Integer pageNum, Integer pageSize, Map<String, Object> search) {

        PageDataResult pageDataResult = new PageDataResult();

        PageHelper.startPage(pageNum,pageSize);
        List<SysScenicSpotServiceRes> list = sysScenicSpotServiceResMapper.getSpotServiceResList(search);

        if (list.size()>0){
            PageInfo<SysScenicSpotServiceRes> pageInfo = new PageInfo<>(list);
            pageDataResult.setCode(200);
            pageDataResult.setData(list);
            pageDataResult.setTotals((int)pageInfo.getTotal());
        }
        return pageDataResult;
    }


    @Override
    public int addSpotServiceRes(SysScenicSpotServiceRes sysScenicSpotServiceRes) {

        sysScenicSpotServiceRes.setServiceId(IdUtils.getSeqId());
        sysScenicSpotServiceRes.setServiceType("1");
        sysScenicSpotServiceRes.setServiceNamePinYin(Tinypinyin.tinypinyin(sysScenicSpotServiceRes.getServiceName()));
        sysScenicSpotServiceRes.setCreateDate(DateUtil.currentDateTime());
        sysScenicSpotServiceRes.setUpdateDate(DateUtil.currentDateTime());
        int i = sysScenicSpotServiceResMapper.insertSelective(sysScenicSpotServiceRes);
        return i;
    }

    /**
     * 修改卫生间信息
     * @param sysScenicSpotServiceRes
     * @return
     */
    @Override
    public int editSpotServiceRes(SysScenicSpotServiceRes sysScenicSpotServiceRes) {

        sysScenicSpotServiceRes.setUpdateDate(DateUtil.currentDateTime());
        int i = sysScenicSpotServiceResMapper.updateByPrimaryKeySelective(sysScenicSpotServiceRes);
        return i;
    }

    /**
     * 后台管理——删除卫生间信息
     * @param serviceId
     * @return
     */
    @Override
    public int delSpotServiceRes(Long serviceId) {
        int i = sysScenicSpotServiceResMapper.deleteByPrimaryKey(serviceId);
        return i;
    }

    /**
     * 后台管理——修改卫生间启用禁用状态
     * @param serviceId
     * @return
     */
    @Override
    public int editSpotServiceResState(Long serviceId, String serviceState) {

        SysScenicSpotServiceRes sysScenicSpotServiceRes = sysScenicSpotServiceResMapper.selectByPrimaryKey(serviceId);
        sysScenicSpotServiceRes.setServiceState(serviceState);
        int i = sysScenicSpotServiceResMapper.updateByPrimaryKeySelective(sysScenicSpotServiceRes);
        return i ;
    }

    /**
     * 根据景区id和服务项名称查询数据
     * @param scenicSpotId
     * @param serviceId
     * @return
     */
    @Override
    public SysScenicSpotServiceRes getSpotIdAndServiceResId(Long scenicSpotId, Long serviceId) {

       SysScenicSpotServiceRes sysScenicSpotServiceRes =  sysScenicSpotServiceResMapper.getSpotIdAndServiceResId(scenicSpotId,serviceId);

       return sysScenicSpotServiceRes;

    }

    /**
     * 定时任务中添加景区服务器数据
     * @param sysScenicSpotServiceRes
     * @return
     */
    @Override
    public int insert(SysScenicSpotServiceRes sysScenicSpotServiceRes) {

        sysScenicSpotServiceRes.setCreateDate(DateUtil.currentDateTime());
        sysScenicSpotServiceRes.setUpdateDate(DateUtil.currentDateTime());
        int i = sysScenicSpotServiceResMapper.insertSelective(sysScenicSpotServiceRes);
        return i;
    }

    /**
     * 根据id查询
     * @param serviceId
     * @return
     */
    @Override
    public SysScenicSpotServiceRes getSpotServiceResId(Long serviceId) {

        SysScenicSpotServiceRes sysScenicSpotServiceRes = sysScenicSpotServiceResMapper.selectByPrimaryKey(serviceId);
        return sysScenicSpotServiceRes;
    }

    /**
     *  导出卫生间
     * @param search
     * @return
     */
    @Override
    public List<SysScenicSpotServiceRes> getServiceResExcelPoi(Map<String, Object> search) {

        List<SysScenicSpotServiceRes> sysScenicSpotServiceResList = sysScenicSpotServiceResMapper.getSpotServiceResList(search);

        return sysScenicSpotServiceResList;


    }
}
