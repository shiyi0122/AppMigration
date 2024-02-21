package com.jxzy.AppMigration.NavigationApp.dao;

import com.jxzy.AppMigration.NavigationApp.entity.SysFeaturedFoodShop;
import com.jxzy.AppMigration.NavigationApp.entity.SysGoodThingsShop;

import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2023/1/10 18:07
 */
public interface SysGoodThingsShopMapper {

    int insertSelective(SysGoodThingsShop sysGoodThingsShop);


    int updateSelective(SysGoodThingsShop sysGoodThingsShop);


    int deleteByPrimaryKey(Long id);

    List<SysGoodThingsShop> list(Map<String, Object> search);


    SysGoodThingsShop selectById(String shopsId);

    int addGiveTheThumbsUp(Long id);

    Integer getUidAndShopIdAndTypeByGoodThings(String uid, int type, Long id);

}
