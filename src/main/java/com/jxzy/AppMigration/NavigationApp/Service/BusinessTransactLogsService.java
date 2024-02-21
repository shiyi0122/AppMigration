package com.jxzy.AppMigration.NavigationApp.Service;

import com.jxzy.AppMigration.NavigationApp.entity.BusinessTransactLogs;

/**
 * @Author zhang
 * @Date 2023/7/25 13:59
 */
public interface BusinessTransactLogsService {

    void insertSelective(BusinessTransactLogs businessTransactLogs);

}
