package com.jxzy.AppMigration.NavigationApp.entity;

import lombok.Data;

/**
 * @Author zhang
 * @Date 2023/4/26 17:37
 * 订单用户信息表
 */
@Data
public class BusinessOrderUserInformation {

    private Long   id ;
    //订单id
    private Long   orderId;
    //门票id
    private String admissionFeeId;
    //用户id
    private Long   appUserId;
    //用户名称
    private String appUserName;
    //用户手机号
    private String userPhone;
    //身份证信息
    private String identityCard;
    //创建时间
    private String createTime;
    //修改时间
    private String updateTime;
    //景区id
    private Long   spotId;
    //景点id
    private Long broadcastId;
    //是否使用1已预约，2已完成，3已失效（暂不使用）
    private String isUse;
    //有效开始时间
    private String effectiveStartDate;
    //有效结束时间
    private String effectiveEndDate;
    //使用日期
    private String usedDate;

    private Long writeUserId;

    //有效天数
    private String effectiveDays;
    //景区名称
    private String scenicSpotName;
    //门票名称
    private String admissionFeeName;
    //门票价格
    private String admissionFeePrice;
    //景点名称
    private String broadcastName;

}
