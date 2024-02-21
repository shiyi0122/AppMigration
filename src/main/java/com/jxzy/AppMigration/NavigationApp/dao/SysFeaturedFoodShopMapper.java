package com.jxzy.AppMigration.NavigationApp.dao;

import com.jxzy.AppMigration.NavigationApp.entity.SysFeaturedFoodShop;

import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2023/1/9 17:33
 */
public interface SysFeaturedFoodShopMapper {


    int insertSelective(SysFeaturedFoodShop sysFeaturedFoodShop);


    int updateSelective(SysFeaturedFoodShop sysFeaturedFoodShop);


    int deleteByPrimaryKey(Long id);

    List<SysFeaturedFoodShop> list(Map<String, Object> search);


    int addGiveTheThumbsUp(Long id);

    SysFeaturedFoodShop selectById(String shopsId);

}
