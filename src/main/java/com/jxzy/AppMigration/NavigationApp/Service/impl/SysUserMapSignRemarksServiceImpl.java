package com.jxzy.AppMigration.NavigationApp.Service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jxzy.AppMigration.NavigationApp.Service.SysUserMapSignRemarksService;
import com.jxzy.AppMigration.NavigationApp.dao.SysUserMapSignRemarksMapper;
import com.jxzy.AppMigration.NavigationApp.entity.SysUserMapSignRemarks;
import com.jxzy.AppMigration.NavigationApp.util.PageDataResult;
import com.jxzy.AppMigration.common.utils.DateUtil;
import com.jxzy.AppMigration.common.utils.IdUtils;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2022/8/10 19:43
 * 用户地图标记备注相关接口
 */
@Service
public class SysUserMapSignRemarksServiceImpl implements SysUserMapSignRemarksService {

    @Autowired
    SysUserMapSignRemarksMapper sysUserMapSignRemarksMapper;


    /**
     * 添加用户地图标记
     * @param sysUserMapSignRemarks
     * @return
     */
    @Override
    public int addUserMapSignRemarks(SysUserMapSignRemarks sysUserMapSignRemarks) {

        sysUserMapSignRemarks.setId(IdUtils.getSeqId());
        sysUserMapSignRemarks.setCreateTime(DateUtil.currentDateTime());
        sysUserMapSignRemarks.setUpdateTime(DateUtil.currentDateTime());

        int i = sysUserMapSignRemarksMapper.addUserMapSignRemarks(sysUserMapSignRemarks);

        return i;

    }
    /**
     * 查询用户标记
     * @param
     * @return
     * zhang
     */
    @Override
    public PageDataResult getUserMapSignRemarksList(Integer pageNum, Integer pageSize, Map<String, Object> search) {

        PageDataResult pageDataResult = new PageDataResult();

        PageHelper.startPage(pageNum,pageSize);

        List<SysUserMapSignRemarks> list = sysUserMapSignRemarksMapper.getUserMapSignRemarksList(search);

        if (list.size()>0){
            PageInfo<SysUserMapSignRemarks> pageInfo = new PageInfo<>(list);
            pageDataResult.setData(list);
            pageDataResult.setTotals((int)pageInfo.getTotal());
            pageDataResult.setCode(200);
        }
        return pageDataResult;
    }
}
