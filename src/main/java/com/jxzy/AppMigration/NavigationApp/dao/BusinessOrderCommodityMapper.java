package com.jxzy.AppMigration.NavigationApp.dao;

import com.jxzy.AppMigration.NavigationApp.entity.BusinessOrderCommodity;

import java.util.List;

/**
 * @Author zhang
 * @Date 2023/4/25 13:39
 */
public interface BusinessOrderCommodityMapper {


    int  insert(List<BusinessOrderCommodity> commodityList);


    List<BusinessOrderCommodity> selectByOrderId(String id);

}
