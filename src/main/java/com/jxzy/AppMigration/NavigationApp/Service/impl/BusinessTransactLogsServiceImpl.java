package com.jxzy.AppMigration.NavigationApp.Service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.jxzy.AppMigration.NavigationApp.Service.BusinessTransactLogsService;
import com.jxzy.AppMigration.NavigationApp.dao.BusinessTransactLogsMapper;
import com.jxzy.AppMigration.NavigationApp.entity.BusinessTransactLogs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author zhang
 * @Date 2023/7/25 13:59
 */
@Service
public class BusinessTransactLogsServiceImpl implements BusinessTransactLogsService {

    @Autowired
    BusinessTransactLogsMapper businessTransactLogsMapper;

    @DS("master2")
    @Override
    public void insertSelective(BusinessTransactLogs businessTransactLogs)  {

        int i = businessTransactLogsMapper.insertSelective(businessTransactLogs);

    }


}
