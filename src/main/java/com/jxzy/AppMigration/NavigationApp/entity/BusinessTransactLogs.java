package com.jxzy.AppMigration.NavigationApp.entity;

import lombok.Data;

import java.math.BigDecimal;

/**
 * * All rights Reserved, Designed By http://www.fenghuaapp.com
 * @Description: Table BUSINESS_TRANSACT_LOGS
 * @date: 2020-01-09 19:15:25
 * @version: V1.0  
 * @Copyright: 2019 http://www.fenghuaapp.com/ Inc. All rights reserved.
 * 注意：本内容仅限于风华正茂科技(北京)有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
@Data
public class BusinessTransactLogs {
    /**
     * 订单编号
     * Column: ID
     */
    private String id;

    /**
     * 用户标识
     * Column: USER_ID
     */
    private Long userId;

    /**
     * 手机号，可用于判断注册是否缴纳保证金
     * Column: PHONE
     */
    private String phone;

    /**
     * 操作对象的id
     * Column: OBJECT_ID
     */
    private Long objectId;

    /**
     * 交易金额
     * Column: PRICE
     */
    private BigDecimal price;

    /**
     * 支付方式 0微信 1支付宝
     * Column: PAY_TYPE
     */
    private Integer payType;

    /**
     * 备注
     * Column: REMARKS
     */
    private String remarks;

    /**
     * 0注册保证金 1竞拍保证金 2限时购保证金 3订阅
     * Column: TYPE
     */
    private Integer type;

    /**
     * -1 删除 0未完成 1已完成
     * Column: STATUS
     */
    private Integer status;

    /**
     * 创建时间
     * Column: CREATE_TIME
     */
    private String createTime;

    /**
     * 更新时间
     * Column: UPDATE_TIME
     */
    private String updateTime;



    /**
     * 商品id
     * Column: UPDATE_TIME
     */
    private String liveId;
}