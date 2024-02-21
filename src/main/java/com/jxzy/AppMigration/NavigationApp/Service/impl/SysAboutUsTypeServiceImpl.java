package com.jxzy.AppMigration.NavigationApp.Service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jxzy.AppMigration.NavigationApp.Service.SysAboutUsTypeService;
import com.jxzy.AppMigration.NavigationApp.dao.SysAboutUsMapper;
import com.jxzy.AppMigration.NavigationApp.dao.SysAboutUsTypeMapper;
import com.jxzy.AppMigration.NavigationApp.entity.SysAboutUsType;
import com.jxzy.AppMigration.NavigationApp.util.PageDataResult;
import com.jxzy.AppMigration.common.utils.DateUtil;
import com.jxzy.AppMigration.common.utils.IdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author zhang
 * @Date 2023/1/14 17:20
 */
@Service
public class SysAboutUsTypeServiceImpl implements SysAboutUsTypeService {

    @Autowired
    SysAboutUsTypeMapper sysAboutUsTypeMapper;
    @Autowired
    SysAboutUsMapper sysAboutUsMapper;

    /**
     *  添加
     * @param sysAboutUsType
     * @return
     */
    @Override
    public int addSysAboutUsType(SysAboutUsType sysAboutUsType) {

        sysAboutUsType.setId(IdUtils.getSeqId());
        sysAboutUsType.setCreateTime(DateUtil.currentDateTime());
        sysAboutUsType.setUpdateTime(DateUtil.currentDateTime());
        int i = sysAboutUsTypeMapper.insert(sysAboutUsType);
        return i;
    }

    /**
     * 修改
     * @param sysAboutUsType
     * @return
     */
    @Override
    public int editSysAboutUsType(SysAboutUsType sysAboutUsType) {

        sysAboutUsType.setUpdateTime(DateUtil.currentDateTime());

        int i = sysAboutUsTypeMapper.update(sysAboutUsType);
        return i;
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @Override
    public int delSysAboutUsType(Long id) {

       int i =  sysAboutUsTypeMapper.delete(id);
       int iN = sysAboutUsMapper.deleteByTypeId(id);

       return i;
    }

    /**
     * 服务项列表查询
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public PageDataResult getSysAboutUsTypeList(Integer pageNum, Integer pageSize) {

        PageDataResult pageDataResult = new PageDataResult();
        PageHelper.startPage(pageNum,pageSize);
        List<SysAboutUsType> list = sysAboutUsTypeMapper.list();
        if (list.size()>0){
            PageInfo<SysAboutUsType> pageInfo = new PageInfo<>(list);
            pageDataResult.setData(list);
            pageDataResult.setTotals((int)pageInfo.getTotal());
            pageDataResult.setCode(200);
        }

        return pageDataResult;

    }

    /**
     *
     * @return
     */
    @Override
    public List<SysAboutUsType> sysAboutUsTypeDropDown() {

        List<SysAboutUsType>  list = sysAboutUsTypeMapper.sysAboutUsTypeDropDown();
        return list;
    }
}
