package com.jxzy.AppMigration.NavigationApp.dao;

import com.jxzy.AppMigration.NavigationApp.entity.BusinessOrder;
import com.jxzy.AppMigration.NavigationApp.entity.BusinessOrderY;

import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2023/4/24 17:09
 */
public interface BusinessOrderMapper {

    int addBusinessOrderY(BusinessOrderY businessOrderY);

    BusinessOrderY selectById(String id);

    List<BusinessOrderY> selectBySearch(Map<String, Object> search);

    BusinessOrderY selectByOrderNumber(String orderNumber);

    int  updateSelective(BusinessOrderY log);

    BusinessOrder selectByOrderNumberZS(String orderNumber);

    int updateSelectiveZS(BusinessOrder log);

    int addBusinessOrderH(BusinessOrder order);


}
