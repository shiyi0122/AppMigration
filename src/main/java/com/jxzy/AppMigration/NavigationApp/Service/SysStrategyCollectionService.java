package com.jxzy.AppMigration.NavigationApp.Service;

import com.jxzy.AppMigration.NavigationApp.entity.SysStrategyCollection;

/**
 * @Author zhang
 * @Date 2023/1/12 16:48
 * 游记，攻略，广场收藏
 */
public interface SysStrategyCollectionService {
    int insert(SysStrategyCollection strategyCollection);

    int delete(Long id,Long userId);

}
