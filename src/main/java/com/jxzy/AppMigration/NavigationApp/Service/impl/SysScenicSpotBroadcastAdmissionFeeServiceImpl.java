package com.jxzy.AppMigration.NavigationApp.Service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jxzy.AppMigration.NavigationApp.Service.SysScenicSpotBroadcastAdmissionFeeService;
import com.jxzy.AppMigration.NavigationApp.dao.SysScenicSpotBroadcastAdmissionFeeMapper;
import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotAdmissionFee;
import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotBroadcastAdmissionFee;
import com.jxzy.AppMigration.NavigationApp.util.PageDataResult;
import com.jxzy.AppMigration.common.utils.DateUtil;
import com.jxzy.AppMigration.common.utils.IdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2023/5/6 10:21
 */
@Service
public class SysScenicSpotBroadcastAdmissionFeeServiceImpl implements SysScenicSpotBroadcastAdmissionFeeService {

    @Autowired
    SysScenicSpotBroadcastAdmissionFeeMapper sysScenicSpotBroadcastAdmissionFeeMapper;

    /**
     * 添加景点门票
     * @param sysScenicSpotBroadcastAdmissionFee
     * @return
     */
    @Override
    public int addBroadcastAdmissionFee(SysScenicSpotBroadcastAdmissionFee sysScenicSpotBroadcastAdmissionFee) {

        sysScenicSpotBroadcastAdmissionFee.setId(IdUtils.getSeqId());
        sysScenicSpotBroadcastAdmissionFee.setCreateTime(DateUtil.currentDateTime());
        sysScenicSpotBroadcastAdmissionFee.setUpdateTime(DateUtil.currentDateTime());
        int i = sysScenicSpotBroadcastAdmissionFeeMapper.insertSelective(sysScenicSpotBroadcastAdmissionFee);

        return i;
    }

    /**
     * 查询景点门票列表
     * @param pageNum
     * @param pageSize
     * @param search
     * @return
     */
    @Override
    public PageDataResult spotBroadcastAdmissionFeeList(Integer pageNum, Integer pageSize, Map<String, Object> search) {

        PageDataResult pageDataResult = new PageDataResult();
        PageHelper.startPage(pageNum,pageSize);
        List<SysScenicSpotBroadcastAdmissionFee> scenicSpotAdmissionFeeList = sysScenicSpotBroadcastAdmissionFeeMapper.selectBySearch(search);
        if (scenicSpotAdmissionFeeList.size() > 0){
            PageInfo<SysScenicSpotBroadcastAdmissionFee> pageInfo = new PageInfo<>(scenicSpotAdmissionFeeList);
            pageDataResult.setData(scenicSpotAdmissionFeeList);
            pageDataResult.setTotals((int)pageInfo.getTotal());
            pageDataResult.setCode(200);
        }
        return pageDataResult;
    }

    /**
     * 修改景点门票
     * @param sysScenicSpotBroadcastAdmissionFee
     * @return
     */
    @Override
    public int editBroadcastAdmissionFee(SysScenicSpotBroadcastAdmissionFee sysScenicSpotBroadcastAdmissionFee) {

        sysScenicSpotBroadcastAdmissionFee.setUpdateTime(DateUtil.currentDateTime());

        int i = sysScenicSpotBroadcastAdmissionFeeMapper.updateSelective(sysScenicSpotBroadcastAdmissionFee);

        return i;

    }

    /**
     * 删除景点门票
     * @param id
     * @return
     */
    @Override
    public int delBroadcastAdmissionFee(Long id) {

        int i = sysScenicSpotBroadcastAdmissionFeeMapper.deleteByPrimaryKey(id);

        return i;

    }
}
