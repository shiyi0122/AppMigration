package com.jxzy.AppMigration.NavigationApp.Service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jxzy.AppMigration.NavigationApp.Service.SysAppSubversionService;
import com.jxzy.AppMigration.NavigationApp.dao.SysAppSubversionMapper;
import com.jxzy.AppMigration.NavigationApp.entity.SysAboutUsType;
import com.jxzy.AppMigration.NavigationApp.entity.SysAppSubversion;
import com.jxzy.AppMigration.NavigationApp.util.PageDataResult;
import com.jxzy.AppMigration.common.utils.DateUtil;
import com.jxzy.AppMigration.common.utils.IdUtils;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author zhang
 * @Date 2023/4/26 9:43
 */
@Service
public class SysAppSubversionServiceImpl implements SysAppSubversionService {

    @Autowired
    SysAppSubversionMapper sysAppSubversionMapper;

    /**
     * 添加app类型版本
     * @param sysAppSubversions
     * @return
     */
    @Override
    public int addSysAppSubversion(SysAppSubversion sysAppSubversions) {

        sysAppSubversions.setId(IdUtils.getSeqId());
        sysAppSubversions.setCreateTime(DateUtil.currentDateTime());
        sysAppSubversions.setUpdateTime(DateUtil.currentDateTime());

        int i = sysAppSubversionMapper.insert(sysAppSubversions);
        return i;
    }

    /**
     * 修改app类型版本
     * @param sysAppSubversions
     * @return
     */
    @Override
    public int editSysAppSubversion(SysAppSubversion sysAppSubversions) {

        sysAppSubversions.setUpdateTime(DateUtil.currentDateTime());

        int i = sysAppSubversionMapper.update(sysAppSubversions);

        return i;
    }

    /**
     * 删除app类型版本
     * @param id
     * @return
     */
    @Override
    public int delSysAppSubversion(Long id) {

       int i = sysAppSubversionMapper.delSysAppSubversion(id);
       return i;
    }

    /**
     * 列表查询
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public PageDataResult getSysAppSubversionList(Integer pageNum, Integer pageSize) {
        PageDataResult pageDataResult = new PageDataResult();
        PageHelper.startPage(pageNum,pageSize);
        List<SysAppSubversion> list = sysAppSubversionMapper.getSysAppSubversionList();

        if (list.size()>0){
            PageInfo<SysAppSubversion> pageInfo = new PageInfo<>(list);
            pageDataResult.setData(list);
            pageDataResult.setTotals((int)pageInfo.getTotal());
            pageDataResult.setCode(200);
        }
        return pageDataResult;
    }

    /**
     * 下拉选
     * @return
     */
    @Override
    public List<SysAppSubversion> sysAppSubversionDropDown() {

       List<SysAppSubversion> list = sysAppSubversionMapper.sysAppSubversionDropDown();
        return list;
    }
}
