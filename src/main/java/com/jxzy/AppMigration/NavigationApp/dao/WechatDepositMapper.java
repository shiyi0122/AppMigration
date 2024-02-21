package com.jxzy.AppMigration.NavigationApp.dao;

import com.jxzy.AppMigration.NavigationApp.entity.WechatDeposit;

/**
 * @Author zhang
 * @Date 2023/8/7 11:23
 */
public interface WechatDepositMapper {

    int insertSelective(WechatDeposit wechatDeposit);

    WechatDeposit getTransactLogsById(String tradeNo);

    int updateWechatDeposit(WechatDeposit transactLogsById);

}
