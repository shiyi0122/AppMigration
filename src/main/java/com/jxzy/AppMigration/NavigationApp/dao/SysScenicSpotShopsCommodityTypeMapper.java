package com.jxzy.AppMigration.NavigationApp.dao;

import com.jxzy.AppMigration.NavigationApp.entity.SysScenicSpotShopsCommodityType;

import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2022/9/6 17:50
 */
public interface SysScenicSpotShopsCommodityTypeMapper {


    List<SysScenicSpotShopsCommodityType> getShopsCommodityTypeList(Map<String, Object> search);


    int insertSelective(SysScenicSpotShopsCommodityType sysScenicSpotShopsCommodityType);


    int updateSelective(SysScenicSpotShopsCommodityType sysScenicSpotShopsCommodityType);


    int deleteByPrimaryKey(Long typeId);

    List<SysScenicSpotShopsCommodityType> shopsCommodityTypeDrop();

    SysScenicSpotShopsCommodityType selectByPrimaryKey(Long typeId);


}
