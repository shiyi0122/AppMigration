package com.jxzy.AppMigration.NavigationApp.entity;

import lombok.Data;

import java.util.List;

/**
 * @Author zhang
 * @Date 2023/4/24 17:24
 */
@Data
public class BusinessOrderY {

    private Long orderId;
    //归属景区id
    private Long spotId;
    //归属店铺id
    private Long businessShopId;
    //订单编号
    private String orderNumber;
    //订单状态1待支付2已支付3已退款
    private String orderStatus;
    //订单金额
    private String actualAmount;
    //折扣
    private String orderDiscount;
    //满减id
    private String fullReduction;
    //订单创建时间
    private String orderStartTime;
    //创建时间
    private String createTime;
    //修改时间
    private String updateTime;
    //退款/取消原因
    private String refundReason;
    //退款/取消进度
    private String retirementRate;
    //游娱go用户id
    private String appUserId;
    //总体评价 1-5颗星
    private String overallEvaluation;
    //支付金额
    private String paymentAmount;
    //折扣金额
    private String discountAmount;
    //满减金额
    private String fullReductionPrice;
    //订单类型 1门票2商品
    private String orderTypesOf;
    //核销状态1，未核销，2已核销，3已过期，4已取消
    private String writeOffStatus;
    //支付时间
    private String paymentTime;

    private String number;

    private String phone;

    private List<BusinessOrderCommodity> commodityList;

    //购买门票列表
    private List<BusinessOrderUserInformation> userInformationList;



}
