package com.jxzy.AppMigration.NavigationApp.Service;

import com.jxzy.AppMigration.NavigationApp.entity.APIReturnResult;
import com.jxzy.AppMigration.NavigationApp.entity.WechatDeposit;

import java.util.Map;

/**
 * @Author zhang
 * @Date 2023/8/7 11:22
 */
public interface WechatDepositService {

//    Map<String, Object> addWechatDeposit(String userId, String scenicSpotId);


    int  insertSelective(WechatDeposit wechatDeposit);

    WechatDeposit getTransactLogsById(String tradeNo);

    APIReturnResult aliPayAndWeiXinOperation(String out_trade_no, String objectId, String type, String payMoney);


    int updateWechatDeposit(WechatDeposit wechatDeposit);

}
