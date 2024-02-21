package com.jxzy.AppMigration.NavigationApp.Service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jxzy.AppMigration.NavigationApp.Service.SysScenicSpotAdmissionFeeService;
import com.jxzy.AppMigration.NavigationApp.dao.SysScenicSpotAdmissionFeeMapper;
import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotAdmissionFee;
import com.jxzy.AppMigration.NavigationApp.util.PageDataResult;
import com.jxzy.AppMigration.common.utils.DateUtil;
import com.jxzy.AppMigration.common.utils.IdUtils;
import net.bytebuddy.implementation.auxiliary.AuxiliaryType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2023/5/6 10:02
 */
@Service
public class SysScenicSpotAdmissionFeeServiceImpl implements SysScenicSpotAdmissionFeeService {

    @Autowired
    SysScenicSpotAdmissionFeeMapper sysScenicSpotAdmissionFeeMapper;

    /**
     * 添加景区门票
     * @param sysScenicSpotAdmissionFee
     * @return
     */
    @Override
    public int addSpotAdmissionFee(SysScenicSpotAdmissionFee sysScenicSpotAdmissionFee) {

        sysScenicSpotAdmissionFee.setId(IdUtils.getSeqId());
        sysScenicSpotAdmissionFee.setCreateTime(DateUtil.currentDateTime());
        sysScenicSpotAdmissionFee.setUpdateTime(DateUtil.currentDateTime());

        int i = sysScenicSpotAdmissionFeeMapper.insertSelective(sysScenicSpotAdmissionFee);
        return i;
    }

    /**
     * 景区门票查询
     * @param pageNum
     * @param pageSize
     * @param search
     * @return
     */
    @Override
    public PageDataResult spotAdmissionFeeList(Integer pageNum, Integer pageSize, Map<String, Object> search) {
        PageDataResult pageDataResult = new PageDataResult();

        PageHelper.startPage(pageNum,pageSize);
        List<SysScenicSpotAdmissionFee> scenicSpotAdmissionFeeList = sysScenicSpotAdmissionFeeMapper.selectBySearch(search);

        if (scenicSpotAdmissionFeeList.size() > 0){
            PageInfo<SysScenicSpotAdmissionFee> pageInfo = new PageInfo<>(scenicSpotAdmissionFeeList);

            pageDataResult.setData(scenicSpotAdmissionFeeList);
            pageDataResult.setTotals((int)pageInfo.getTotal());
            pageDataResult.setCode(200);
        }
        return pageDataResult;

    }
    /**
     * 景区门票修改
     * @param
     * @return
     */
    @Override
    public int editSpotAdmissionFee(SysScenicSpotAdmissionFee sysScenicSpotAdmissionFee) {

        sysScenicSpotAdmissionFee.setUpdateTime(DateUtil.currentDateTime());
        int i = sysScenicSpotAdmissionFeeMapper.updateSelective(sysScenicSpotAdmissionFee);
        return i;
    }

    /**
     * 删除景区门票
     * @param id
     * @return
     */
    @Override
    public int delSpotAdmissionFee(Long id) {

       int i =  sysScenicSpotAdmissionFeeMapper.deleteByPrimaryKey(id);

       return i;
    }
}
