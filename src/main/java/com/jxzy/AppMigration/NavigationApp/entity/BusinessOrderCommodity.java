package com.jxzy.AppMigration.NavigationApp.entity;

import lombok.Data;

/**
 * @Author zhang
 * @Date 2023/4/25 13:28
 */
@Data
public class BusinessOrderCommodity {

    private Long id;
    //订单id
    private Long orderId;
    //店铺商品id
    private Long orderCommodityId;
    //商品分类id
    private Long commodityClassifyId;
    //商品名称
    private String orderCommodityName;
    //商品价格
    private String orderCommodityPrice;
    //商品数量
    private String commodityQuantity;
    //创建时间
    private String createTime;
    //修改时间
    private String updateTime;

}
