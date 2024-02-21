package com.jxzy.AppMigration.NavigationApp.dao;



import com.jxzy.AppMigration.NavigationApp.entity.BusinessUserLookTime;

import java.util.Map;

/**
 * @Author zhang
 * @Date 2023/5/23 15:55
 */
public interface BusinessUserLookTimeMapper {
    BusinessUserLookTime selectBySearch(Map<String, Object> search);

    int updateSelective(BusinessUserLookTime businessUserLookTime);


    int  insertSelective(BusinessUserLookTime businessUserLookTime1);

}
