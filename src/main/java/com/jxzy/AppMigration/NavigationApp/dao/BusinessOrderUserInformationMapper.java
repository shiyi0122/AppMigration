package com.jxzy.AppMigration.NavigationApp.dao;

import com.jxzy.AppMigration.NavigationApp.entity.BusinessOrderUserInformation;

import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2023/4/27 9:47
 */
public interface BusinessOrderUserInformationMapper {

    int  insert(BusinessOrderUserInformation businessOrderUserInformation);


    List<BusinessOrderUserInformation> getBusinessOrderId(String id);

    List<BusinessOrderUserInformation> selectBySearch(Map<String, Object> search);

    BusinessOrderUserInformation selectById(Map<String, Object> search);

    int cancellationReservation(String id);

    List<BusinessOrderUserInformation> getBusinessOrderIsUse(String isUse);

    int  update(BusinessOrderUserInformation businessOrderUserInformation);

}
