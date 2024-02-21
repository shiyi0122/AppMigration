package com.jxzy.AppMigration.NavigationApp.dao;

import com.jxzy.AppMigration.NavigationApp.entity.SysStrategy;
import com.jxzy.AppMigration.NavigationApp.entity.SysStrategyCollection;

import java.util.List;

/**
 * @Author zhang
 * @Date 2023/1/12 16:50
 */
public interface SysStrategyCollectionMapper {
    int insert(SysStrategyCollection strategyCollection);

    int delete(Long id, Long userId);

    Integer selectStrategyNumber(Long id,String type);

    Integer selectStrategyByFansNumber(String uid);

    Integer selectStrategyByCollectionNumber(String uid);

    List<SysStrategy> myCollection(String uid, String type);

    Integer getUserIsCollection(String uid, Long id);
}
