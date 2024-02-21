package com.jxzy.AppMigration.NavigationApp.Service;

import com.jxzy.AppMigration.NavigationApp.entity.SysGuideAppAgreement;
import com.jxzy.AppMigration.NavigationApp.util.PageDataResult;

/**
 * @Author zhang
 * @Date 2022/7/22 17:39
 */
public interface SysGuideAppAgreementService {


    SysGuideAppAgreement getSysGuideAppAgreement(String type);


    int addSysGuideAppAgreement(SysGuideAppAgreement sysGuideAppAgreement);

    int editSysGuideAppAgreement(SysGuideAppAgreement sysGuideAppAgreement);

    PageDataResult getSysGuideAppAgreementList(Integer pageNum, Integer pageSize);

}
