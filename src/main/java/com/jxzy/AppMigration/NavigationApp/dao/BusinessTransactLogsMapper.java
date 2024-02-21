package com.jxzy.AppMigration.NavigationApp.dao;


import com.jxzy.AppMigration.NavigationApp.entity.BusinessTransactLogs;

import java.util.Map;

/**
 * * All rights Reserved, Designed By http://www.fenghuaapp.com
 * @Description: BusinessTransactLogsMapper
 * @date: 2020-01-09 19:15:25
 * @version: V1.0  
 * @Copyright: 2019 http://www.fenghuaapp.com/ Inc. All rights reserved.
 * 注意：本内容仅限于风华正茂科技(北京)有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
public interface BusinessTransactLogsMapper {
    int deleteByPrimaryKey(String id);

    int insertSelective(BusinessTransactLogs record);

    BusinessTransactLogs selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(BusinessTransactLogs record);

    /***
     * 根据手机号查询是否支付过保证金
     * @param phone
     * @return
     */
    Integer getTransactLogsCountByPhone(String phone);

    /***
     * 获取用户是否交拿保证金
     * @param search
     * @return
     */
    Integer getTransactLogsCountBySearch(Map<String, String> search);
}