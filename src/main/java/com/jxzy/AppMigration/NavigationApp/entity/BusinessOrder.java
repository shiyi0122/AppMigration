package com.jxzy.AppMigration.NavigationApp.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Author zhang
 * @Date 2023/4/24 17:24
 */
@Data
public class BusinessOrder {

    /**
     * Column: ID
     */
    private Long id;

    /**
     * 用户id
     * Column: USER_ID
     */
    private Long userId;

    /**
     * 限时购/竞拍购买ID
     * Column: AUTION_BUY_ID
     */
    private Long autionBuyId;

    /**
     * 订单编号
     * Column: ORDER_NUMBER
     */
    private String orderNumber;

    /**
     * 订单金额
     * Column: ORDER_AMOUNT
     */
    private BigDecimal orderAmount;

    /**
     * 支付方式 0微信 1支付宝 2线下购买
     * Column: PAY_TYPE
     */
    private Integer payType;

    /**
     * 订单状态 0未完成 1已完成
     * Column: ORDER_STATUS
     */
    private Integer orderStatus;

    /**
     * 发票状态0未开发票 1已开发票
     * Column: INVOICE_STATUS
     */
    private Integer invoiceStatus;

    /**
     * 0注册保证金 1竞拍保证金 2限时购保证金 3线下商品
     * Column: ORDER_TYPE
     */
    private Integer orderType;

    private String orderTypeName;

    private String payTypeName;

//    public String getOrderTypeName() {
//        if(orderType!=null){
//            orderTypeName = TermManager.getName("order_type",orderType.toString());
//        }
//        return orderTypeName;
//    }
//
//    public String getPayTypeName() {
//        if(payType!=null){
//            payTypeName = TermManager.getName("pay_type",payType.toString());
//        }
//        return payTypeName;
//    }

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


}
