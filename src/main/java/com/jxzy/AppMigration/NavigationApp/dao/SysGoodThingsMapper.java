package com.jxzy.AppMigration.NavigationApp.dao;

import com.jxzy.AppMigration.NavigationApp.entity.SysFeaturedFood;
import com.jxzy.AppMigration.NavigationApp.entity.SysGoodThings;
import com.jxzy.AppMigration.NavigationApp.entity.SysGoodThingsShop;

import java.util.List;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2023/1/10 19:06
 */
public interface SysGoodThingsMapper {

    int insertSelective(SysGoodThingsShop sysGoodThingsShop);


    int updateSelective(SysGoodThingsShop sysGoodThingsShop);

    int deleteByPrimaryKey(Long id);

    List<SysGoodThings> list(Map<String, Object> search);

}
