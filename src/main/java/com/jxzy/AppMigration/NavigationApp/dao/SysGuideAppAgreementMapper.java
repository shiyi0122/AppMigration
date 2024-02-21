package com.jxzy.AppMigration.NavigationApp.dao;

import com.jxzy.AppMigration.NavigationApp.entity.SysGuideAppAgreement;

import java.util.List;

/**
 * @Author zhang
 * @Date 2022/7/22 17:47
 */
public interface SysGuideAppAgreementMapper {

    SysGuideAppAgreement getSysGuideAppAgreement(String type);


    int insert(SysGuideAppAgreement sysGuideAppAgreement);


    int update(SysGuideAppAgreement sysGuideAppAgreement);

    List<SysGuideAppAgreement> list();


}
