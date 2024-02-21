package com.jxzy.AppMigration.NavigationApp.Service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jxzy.AppMigration.NavigationApp.Service.SysScenicSpotAdmissionFeeNoticeService;
import com.jxzy.AppMigration.NavigationApp.dao.SysScenicSpotAdmissionFeeNoticeMapper;
import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotAdmissionFee;
import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotAdmissionFeeNotice;
import com.jxzy.AppMigration.NavigationApp.util.PageDataResult;
import com.jxzy.AppMigration.common.utils.DateUtil;
import com.jxzy.AppMigration.common.utils.IdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2023/5/8 10:12
 * 购票须知
 */
@Service
public class SysScenicSpotAdmissionFeeNoticeServiceImpl implements SysScenicSpotAdmissionFeeNoticeService {

    @Autowired
    SysScenicSpotAdmissionFeeNoticeMapper sysScenicSpotAdmissionFeeNoticeMapper;

    /**
     * 添加购票须知
     * @param sysScenicSpotAdmissionFeeNotice
     * @return
     */
    @Override
    public int addSpotAdmissionFeeNotice(SysScenicSpotAdmissionFeeNotice sysScenicSpotAdmissionFeeNotice) {

        sysScenicSpotAdmissionFeeNotice.setId(IdUtils.getSeqId());
        sysScenicSpotAdmissionFeeNotice.setCreateTime(DateUtil.currentDateTime());
        sysScenicSpotAdmissionFeeNotice.setUpdateTime(DateUtil.currentDateTime());
        int i = sysScenicSpotAdmissionFeeNoticeMapper.insertSelective(sysScenicSpotAdmissionFeeNotice);

        return i;
    }

    /**
     * 修改购票须知
     * @param sysScenicSpotAdmissionFeeNotice
     * @return
     */
    @Override
    public int editSpotAdmissionFeeNotice(SysScenicSpotAdmissionFeeNotice sysScenicSpotAdmissionFeeNotice) {


        sysScenicSpotAdmissionFeeNotice.setUpdateTime(DateUtil.currentDateTime());
        int i = sysScenicSpotAdmissionFeeNoticeMapper.updateSelective(sysScenicSpotAdmissionFeeNotice);
        return i;

    }

    /**
     * 删除购票须知
     * @param id
     * @return
     */
    @Override
    public int delSpotAdmissionFeeNotice(Long id) {

       int i = sysScenicSpotAdmissionFeeNoticeMapper.deleteByPrimaryKey(id);

       return i;
    }

    /**
     * 购票须知列表查询
     * @param pageNum
     * @param pageSize
     * @param search
     * @return
     */
    @Override
    public PageDataResult getSpotAdmissionFeeNoticeList(Integer pageNum, Integer pageSize, Map<Object, Object> search) {

        PageDataResult pageDataResult = new PageDataResult();

        PageHelper.startPage(pageNum,pageSize);
        List<SysScenicSpotAdmissionFeeNotice> list = sysScenicSpotAdmissionFeeNoticeMapper.selectBySearch(search);

        if (list.size() > 0){
            PageInfo<SysScenicSpotAdmissionFeeNotice> pageInfo = new PageInfo<>(list);
            pageDataResult.setData(list);
            pageDataResult.setTotals((int)pageInfo.getTotal());
            pageDataResult.setCode(200);
        }

        return pageDataResult;

    }
}
