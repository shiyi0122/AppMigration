package com.jxzy.AppMigration.NavigationApp.entity;

import lombok.Data;

/**
 * @Author zhang
 * @Date 2023/8/7 11:23
 */
@Data
public class WechatDeposit {

    private Long  depositId;

    private Long  depositUserId;

    private String depositMoney;

    private Long  depositScenicSpotId;

    private String depositState;

    private String outTradeNo;

    private String tradeNo;

    private String  spbillCreateIp;

    private String returnResultCode;

    private String requestNonceStr;

    private String  returnNonceStr;

    private String  requestSign;

    private String returnSign;

    private String  returnPrepayId;

    private String  couponFee;

    private String paymentClient;

    private String   createDate;

    private String updateDate;
}
