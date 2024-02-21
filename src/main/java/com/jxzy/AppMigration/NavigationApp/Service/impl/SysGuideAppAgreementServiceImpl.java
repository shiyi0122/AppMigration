package com.jxzy.AppMigration.NavigationApp.Service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jxzy.AppMigration.NavigationApp.Service.SysGuideAppAgreementService;
import com.jxzy.AppMigration.NavigationApp.dao.SysGuideAppAgreementMapper;
import com.jxzy.AppMigration.NavigationApp.entity.SysAboutUs;
import com.jxzy.AppMigration.NavigationApp.entity.SysGuideAppAgreement;
import com.jxzy.AppMigration.NavigationApp.util.PageDataResult;
import com.jxzy.AppMigration.common.utils.DateUtil;
import com.jxzy.AppMigration.common.utils.IdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author zhang
 * @Date 2022/7/22 17:40
 */

@Service
public class SysGuideAppAgreementServiceImpl implements SysGuideAppAgreementService {

    @Autowired
    SysGuideAppAgreementMapper sysGuideAppAgreementMapper;

    /**
     * 查询使用协议
     * @return
     */
    @Override
    public SysGuideAppAgreement getSysGuideAppAgreement(String type) {

       SysGuideAppAgreement sysGuideAppAgreement =  sysGuideAppAgreementMapper.getSysGuideAppAgreement(type);

       return sysGuideAppAgreement;



    }

    /**
     * 新增协议
     * @param sysGuideAppAgreement
     * @return
     */
    @Override
    public int addSysGuideAppAgreement(SysGuideAppAgreement sysGuideAppAgreement) {

        sysGuideAppAgreement.setId(IdUtils.getSeqId());
        sysGuideAppAgreement.setCreateTime(DateUtil.currentDateTime());
        sysGuideAppAgreement.setUpdateTime(DateUtil.currentDateTime());
        int i = sysGuideAppAgreementMapper.insert(sysGuideAppAgreement);

        return i;
    }

    /**
     * 修改协议
     * @param sysGuideAppAgreement
     * @return
     */
    @Override
    public int editSysGuideAppAgreement(SysGuideAppAgreement sysGuideAppAgreement) {

        sysGuideAppAgreement.setUpdateTime(DateUtil.currentDateTime());
        int i = sysGuideAppAgreementMapper.update(sysGuideAppAgreement);

        return i;
    }

    @Override
    public PageDataResult getSysGuideAppAgreementList(Integer pageNum, Integer pageSize) {

        PageDataResult pageDataResult = new PageDataResult();
        PageHelper.startPage(pageNum,pageSize);
        List<SysGuideAppAgreement> list = sysGuideAppAgreementMapper.list();
        if (list.size()>0){
            PageInfo<SysGuideAppAgreement> pageInfo = new PageInfo<>(list);
            pageDataResult.setData(list);
            pageDataResult.setTotals((int)pageInfo.getTotal());
            pageDataResult.setCode(200);
        }
        return pageDataResult;
    }
}
