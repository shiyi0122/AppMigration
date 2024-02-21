package com.jxzy.AppMigration.NavigationApp.dao;

import com.jxzy.AppMigration.NavigationApp.entity.SysFeaturedFood;

import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2023/1/10 13:26
 * 特色美食店铺商品
 */
public interface SysFeaturedFoodMapper {

    int insertSelective(SysFeaturedFood sysFeaturedFood);


    int updateSelective(SysFeaturedFood sysFeaturedFood);

    int deleteByPrimaryKey(Long id);

    List<SysFeaturedFood> list(Map<String, Object> search);

    int addGiveTheThumbsUp(Long id);

}
