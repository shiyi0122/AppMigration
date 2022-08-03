package com.jxzy.AppMigration.NavigationApp.Service.impl;

import com.jxzy.AppMigration.NavigationApp.Service.SysGuideAppAgreementService;
import com.jxzy.AppMigration.NavigationApp.dao.SysGuideAppAgreementMapper;
import com.jxzy.AppMigration.NavigationApp.entity.SysGuideAppAgreement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
